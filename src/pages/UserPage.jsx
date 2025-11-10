import React, { useState } from "react";
import UserList from "../components/UserList.jsx";
import UserForm from "../components/UserForm.jsx";
import { createUser, updateUser } from "../api/api.jsx";

const UsersPage = () => {
  const [editingUser, setEditingUser] = useState(null);
  const [refreshKey, setRefreshKey] = useState(0);

  const handleEdit = (user) => setEditingUser(user);
  const handleCancel = () => setEditingUser(null);

  const handleSubmit = async (user) => {
    try {
      if (user.id) {
        await updateUser(user.id, user);
      } else {
        await createUser(user);
      }
      setEditingUser(null);
      setRefreshKey(prev => prev + 1); // refresh list
    } catch (err) {
      console.error(err);
      alert("Error saving user");
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-3xl mb-4">Users Management</h1>
      {editingUser ? (
        <UserForm user={editingUser} onSubmit={handleSubmit} onCancel={handleCancel} />
      ) : null}
      <UserList key={refreshKey} onEdit={handleEdit} />
    </div>
  );
};

export default UsersPage;
