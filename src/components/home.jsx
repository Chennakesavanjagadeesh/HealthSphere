import React from "react";
import { Link } from "react-router-dom";
import { Calendar, Stethoscope, ShieldCheck, Video } from "lucide-react";

const HomePage = () => {
  return (
    <div className="min-h-screen flex flex-col bg-gray-50">
      {/* Navbar */}
      <header className="bg-white shadow-md p-4 flex justify-between items-center">
        <h1 className="text-2xl font-bold text-blue-600">HealthSphere</h1>
        <nav className="space-x-6">
          <a href="#features" className="hover:text-blue-600">Features</a>
          <a href="#about" className="hover:text-blue-600">About</a>
          <a href="#contact" className="hover:text-blue-600">Contact</a>
          <Link
            to="/login"
            className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
          >
            Login
          </Link>
        </nav>
      </header>

      {/* Hero Section */}
      <section className="flex flex-col md:flex-row items-center justify-between p-12 bg-gradient-to-r from-blue-50 to-blue-100">
        <div className="max-w-lg">
          <h2 className="text-4xl font-bold text-gray-800 mb-4">
            Your Health. Your Records. Anywhere.
          </h2>
          <p className="text-gray-600 mb-6">
            HealthSphere is a secure EHR & Telemedicine platform that connects
            doctors and patients, simplifies health record management, and brings
            care to your fingertips.
          </p>
          <Link
            to="/login"
            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
          >
            Get Started
          </Link>
        </div>
        <img
          src="https://cdn-icons-png.flaticon.com/512/2966/2966487.png"
          alt="healthcare"
          className="w-80 mt-8 md:mt-0"
        />
      </section>

      {/* Features Section */}
      <section id="features" className="p-12 bg-white">
        <h3 className="text-3xl font-semibold text-center mb-10">Key Features</h3>
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 text-center">
          <div className="p-6 shadow-md rounded-lg hover:shadow-xl transition">
            <ShieldCheck className="mx-auto text-blue-600" size={40} />
            <h4 className="text-xl font-semibold mt-4">HIPAA Security</h4>
            <p className="text-gray-600">Multi-factor authentication & role-based access.</p>
          </div>
          <div className="p-6 shadow-md rounded-lg hover:shadow-xl transition">
            <Calendar className="mx-auto text-blue-600" size={40} />
            <h4 className="text-xl font-semibold mt-4">Appointments</h4>
            <p className="text-gray-600">Book, reschedule, and manage consultations.</p>
          </div>
          <div className="p-6 shadow-md rounded-lg hover:shadow-xl transition">
            <Video className="mx-auto text-blue-600" size={40} />
            <h4 className="text-xl font-semibold mt-4">Telemedicine</h4>
            <p className="text-gray-600">Secure video consultations anytime, anywhere.</p>
          </div>
          <div className="p-6 shadow-md rounded-lg hover:shadow-xl transition">
            <Stethoscope className="mx-auto text-blue-600" size={40} />
            <h4 className="text-xl font-semibold mt-4">EHR Management</h4>
            <p className="text-gray-600">Centralized history, labs, and prescriptions.</p>
          </div>
        </div>
      </section>

      {/* About Section */}
      <section id="about" className="p-12 bg-gray-50 text-center">
        <h3 className="text-3xl font-semibold mb-4">About HealthSphere</h3>
        <p className="text-gray-600 max-w-2xl mx-auto">
          HealthSphere bridges the gap between patients and healthcare providers
          by offering a modern platform for secure medical data access,
          efficient appointment scheduling, and virtual care delivery.
        </p>
      </section>

      {/* Footer */}
      <footer className="bg-blue-600 text-white text-center p-4 mt-auto">
        <p>© 2025 HealthSphere. All Rights Reserved.</p>
      </footer>
    </div>
  );
};

export default HomePage;


