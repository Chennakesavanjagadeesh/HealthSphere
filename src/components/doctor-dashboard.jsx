import React from "react";

const DoctorDashboard = () => (
  <div className="min-h-screen bg-gray-50 p-6">
    {/* Welcome & Profile */}
    <section className="bg-white rounded-lg shadow p-6 mb-6 flex flex-col md:flex-row md:items-center justify-between">
      <div>
        <h1 className="text-2xl font-bold text-blue-700">Welcome, Dr. Jane Smith</h1>
        <p className="text-gray-600 mt-1">Cardiologist | HealthSphere Clinic</p>
      </div>
      <div className="mt-4 md:mt-0 flex gap-2">
        <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Edit Profile</button>
        <button className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">Set Availability</button>
      </div>
    </section>

    {/* Upcoming Appointments */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Upcoming Appointments</h2>
      <table className="w-full text-left">
        <thead>
          <tr>
            <th className="py-2">Patient</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr className="border-t">
            <td>John Doe</td>
            <td>2025-09-12</td>
            <td>10:00 AM</td>
            <td><span className="px-2 py-1 bg-yellow-100 text-yellow-800 rounded">Requested</span></td>
            <td>
              <button className="text-green-600 hover:underline mr-2">Confirm</button>
              <button className="text-blue-600 hover:underline mr-2">Reschedule</button>
              <button className="text-red-600 hover:underline">Cancel</button>
            </td>
          </tr>
          <tr>
            <td>Mary Lee</td>
            <td>2025-09-12</td>
            <td>11:30 AM</td>
            <td><span className="px-2 py-1 bg-green-100 text-green-800 rounded">Confirmed</span></td>
            <td>
              <button className="text-blue-600 hover:underline mr-2">Reschedule</button>
              <button className="text-red-600 hover:underline">Cancel</button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>

    {/* Patient Management */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Patient Management</h2>
      <input
        type="text"
        placeholder="Search patients..."
        className="border px-3 py-2 rounded w-full mb-4"
      />
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="border rounded p-4">
          <h3 className="font-semibold">John Doe</h3>
          <p className="text-sm text-gray-600">Age: 45 | Male</p>
          <button className="mt-2 text-blue-600 hover:underline">View Profile & EHR</button>
        </div>
        <div className="border rounded p-4">
          <h3 className="font-semibold">Mary Lee</h3>
          <p className="text-sm text-gray-600">Age: 38 | Female</p>
          <button className="mt-2 text-blue-600 hover:underline">View Profile & EHR</button>
        </div>
      </div>
    </section>

    {/* EHR Access & Updates */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">EHR Management</h2>
      <ul className="list-disc ml-6 text-gray-700">
        <li>View and update diagnoses, medications, allergies, lab results, visit notes</li>
        <li>Upload/view attachments (scans, PDFs)</li>
        <li>Audit trail for EHR modifications</li>
      </ul>
      <button className="mt-4 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Add EHR Entry</button>
    </section>

    {/* Telemedicine Sessions */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Telemedicine Sessions</h2>
      <div className="flex flex-col md:flex-row gap-4">
        <button className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Start Video Consultation</button>
        <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Start Audio Consultation</button>
        <button className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">In-session Chat</button>
      </div>
      <div className="mt-4 flex gap-2">
        <label className="flex items-center">
          <input type="checkbox" className="mr-2" />
          Consent for recording
        </label>
        <button className="bg-gray-200 px-3 py-1 rounded hover:bg-gray-300">Start Recording</button>
      </div>
    </section>

    {/* E-Prescribing */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">E-Prescribing</h2>
      <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Create Prescription</button>
      <ul className="mt-4 text-gray-700">
        <li>View prescription history for patients</li>
        <li>Digitally sign and send prescriptions</li>
      </ul>
    </section>

    {/* Notifications & Reminders */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Notifications & Reminders</h2>
      <ul className="text-gray-700">
        <li>Appointment reminder: John Doe at 10:00 AM</li>
        <li>Message from Admin: "System maintenance scheduled for 2025-09-15"</li>
        <li>New appointment request from Mary Lee</li>
      </ul>
    </section>

    {/* Availability Management */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Availability Management</h2>
      <button className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">Update Available Slots</button>
      <button className="ml-2 bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700">Set Leave Days</button>
    </section>

    {/* Reports & Analytics */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Reports & Analytics</h2>
      <ul className="text-gray-700">
        <li>Appointments this month: <span className="font-bold">24</span></li>
        <li>Active patients: <span className="font-bold">18</span></li>
        <li>Downloadable reports</li>
      </ul>
      <button className="mt-2 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Download Report</button>
    </section>

    {/* Security & Compliance */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Security & Compliance</h2>
      <ul className="text-gray-700">
        <li>Audit log access</li>
        <li>MFA status: <span className="font-bold text-green-600">Enabled</span></li>
      </ul>
      <button className="mt-2 bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700">View Audit Logs</button>
    </section>

    {/* Optional/Advanced Features */}
    <section className="bg-white rounded-lg shadow p-6 mb-6">
      <h2 className="text-xl font-semibold text-blue-700 mb-4">Advanced Features</h2>
      <ul className="text-gray-700">
        <li>System-wide announcements</li>
        <li>Quick links to support/help</li>
      </ul>
      <button className="mt-2 bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">Contact Support</button>
    </section>

    {/* Logout */}
    <div className="flex justify-end">
      <button className="bg-red-600 text-white px-6 py-2 rounded hover:bg-red-700">Logout</button>
    </div>
  </div>
);

export default DoctorDashboard;