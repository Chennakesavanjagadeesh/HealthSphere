import React, { useEffect, useState } from "react";
import { getAllUsers, deleteUser, toggleMFA } from "../api/api.jsx";

const UserList = ({ onEdit }) => {
  const [users, setUsers] = useState([]);

  const fetchUsers = async () => {
    try {
      const res = await getAllUsers();
      setUsers(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure to delete this user?")) {
      await deleteUser(id);
      fetchUsers();
    }
  };

  const handleToggleMFA = async (id, enabled) => {
    await toggleMFA(id, !enabled);
    fetchUsers();
  };

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full border">
        <thead className="bg-gray-100">
          <tr>
            <th className="border px-2 py-1">ID</th>
            <th className="border px-2 py-1">Name</th>
            <th className="border px-2 py-1">Email</th>
            <th className="border px-2 py-1">Role</th>
            <th className="border px-2 py-1">Status</th>
            <th className="border px-2 py-1">MFA</th>
            <th className="border px-2 py-1">Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id} className="hover:bg-gray-50">
              <td className="border px-2 py-1">{u.id}</td>
              <td className="border px-2 py-1">{u.firstName} {u.lastName}</td>
              <td className="border px-2 py-1">{u.email}</td>
              <td className="border px-2 py-1">{u.role}</td>
              <td className="border px-2 py-1">{u.status}</td>
              <td className="border px-2 py-1">
                <button
                  className={`px-2 py-1 rounded ${u.mfaEnabled ? "bg-green-500" : "bg-gray-400"}`}
                  onClick={() => handleToggleMFA(u.id, u.mfaEnabled)}
                >
                  {u.mfaEnabled ? "Enabled" : "Disabled"}
                </button>
              </td>
              <td className="border px-2 py-1 flex gap-2">
                <button
                  onClick={() => onEdit(u)}
                  className="px-2 py-1 bg-blue-500 text-white rounded"
                >Edit</button>
                <button
                  onClick={() => handleDelete(u.id)}
                  className="px-2 py-1 bg-red-500 text-white rounded"
                >Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserList;
