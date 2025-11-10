import React, { useState, useEffect } from 'react';
import { 
  Calendar, 
  User, 
  FileText, 
  Video, 
  Pill, 
  Bell, 
  CreditCard, 
  BookOpen, 
  Shield,
  Edit,
  Phone,
  Mail,
  MapPin,
  Clock,
  Download,
  Plus,
  X,
  Check,
  AlertCircle
} from 'lucide-react';

const PatientPortal = () => {
  const [activeTab, setActiveTab] = useState('profile');
  const [notifications, setNotifications] = useState(3);
  const [showNotifications, setShowNotifications] = useState(false);

  // Mock patient data
  const patientData = {
    name: "Sarah Johnson",
    age: 34,
    phone: "(555) 123-4567",
    email: "sarah.johnson@email.com",
    address: "123 Main St, Anytown, ST 12345",
    emergencyContact: {
      name: "Michael Johnson",
      relationship: "Spouse",
      phone: "(555) 987-6543"
    },
    appointments: [
      { id: 1, date: "2024-03-20", time: "10:00 AM", doctor: "Dr. Smith", type: "Annual Checkup", status: "Confirmed" },
      { id: 2, date: "2024-03-25", time: "2:30 PM", doctor: "Dr. Brown", type: "Follow-up", status: "Requested" },
      { id: 3, date: "2024-04-01", time: "11:15 AM", doctor: "Dr. Wilson", type: "Consultation", status: "Confirmed" }
    ],
    healthRecords: [
      { type: "Diagnosis", date: "2024-02-15", description: "Hypertension - Stage 1", doctor: "Dr. Smith" },
      { type: "Lab Result", date: "2024-02-10", description: "Blood Panel - Normal", doctor: "Dr. Smith" },
      { type: "Medication", date: "2024-02-15", description: "Lisinopril 10mg", doctor: "Dr. Smith" }
    ],
    prescriptions: [
      { id: 1, medication: "Lisinopril 10mg", status: "Active", pharmacy: "City Pharmacy", refillsLeft: 3 },
      { id: 2, medication: "Vitamin D3", status: "Ready for Pickup", pharmacy: "City Pharmacy", refillsLeft: 2 }
    ]
  };

  const TabButton = ({ id, icon: Icon, label, count }) => (
    <button
      onClick={() => setActiveTab(id)}
      className={`flex items-center gap-2 px-4 py-2 rounded-lg transition-all relative ${
        activeTab === id 
          ? 'bg-blue-500 text-white shadow-md' 
          : 'text-gray-600 hover:bg-gray-100'
      }`}
    >
      <Icon size={18} />
      <span className="hidden md:inline">{label}</span>
      {count && (
        <span className="bg-red-500 text-white text-xs rounded-full px-2 py-0.5 min-w-5 text-center">
          {count}
        </span>
      )}
    </button>
  );

  const ProfileTab = () => (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-blue-500 to-purple-600 text-white p-6 rounded-xl">
        <h1 className="text-2xl font-bold mb-2">Welcome back, {patientData.name}!</h1>
        <p className="opacity-90">Here's your health dashboard overview</p>
      </div>
      
      <div className="grid md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold flex items-center gap-2">
              <User className="text-blue-500" size={20} />
              Personal Information
            </h2>
            <button className="text-blue-500 hover:bg-blue-50 p-2 rounded-lg">
              <Edit size={16} />
            </button>
          </div>
          <div className="space-y-3">
            <div className="flex items-center gap-2 text-gray-600">
              <User size={16} />
              <span>{patientData.name}, {patientData.age} years old</span>
            </div>
            <div className="flex items-center gap-2 text-gray-600">
              <Phone size={16} />
              <span>{patientData.phone}</span>
            </div>
            <div className="flex items-center gap-2 text-gray-600">
              <Mail size={16} />
              <span>{patientData.email}</span>
            </div>
            <div className="flex items-center gap-2 text-gray-600">
              <MapPin size={16} />
              <span>{patientData.address}</span>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h2 className="text-lg font-semibold mb-4 flex items-center gap-2">
            <AlertCircle className="text-red-500" size={20} />
            Emergency Contact
          </h2>
          <div className="space-y-3">
            <div className="flex items-center gap-2 text-gray-600">
              <User size={16} />
              <span>{patientData.emergencyContact.name}</span>
            </div>
            <div className="flex items-center gap-2 text-gray-600">
              <span className="text-sm bg-gray-100 px-2 py-1 rounded">
                {patientData.emergencyContact.relationship}
              </span>
            </div>
            <div className="flex items-center gap-2 text-gray-600">
              <Phone size={16} />
              <span>{patientData.emergencyContact.phone}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

  const AppointmentsTab = () => (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold">Appointments</h2>
        <button className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 flex items-center gap-2">
          <Plus size={16} />
          Book Appointment
        </button>
      </div>
      
      <div className="grid gap-4">
        {patientData.appointments.map((apt) => (
          <div key={apt.id} className="bg-white p-4 rounded-xl shadow-sm border">
            <div className="flex justify-between items-start">
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-2">
                  <Calendar size={16} className="text-blue-500" />
                  <span className="font-semibold">{apt.date}</span>
                  <Clock size={16} className="text-gray-400" />
                  <span className="text-gray-600">{apt.time}</span>
                </div>
                <p className="font-medium">{apt.type}</p>
                <p className="text-gray-600">with {apt.doctor}</p>
              </div>
              <div className="flex flex-col items-end gap-2">
                <span className={`px-3 py-1 rounded-full text-sm ${
                  apt.status === 'Confirmed' 
                    ? 'bg-green-100 text-green-700' 
                    : 'bg-yellow-100 text-yellow-700'
                }`}>
                  {apt.status}
                </span>
                <div className="flex gap-2">
                  <button className="text-blue-500 text-sm hover:underline">Reschedule</button>
                  <button className="text-red-500 text-sm hover:underline">Cancel</button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );

  const EHRTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Health Records</h2>
      
      <div className="grid gap-4">
        {patientData.healthRecords.map((record, index) => (
          <div key={index} className="bg-white p-4 rounded-xl shadow-sm border">
            <div className="flex justify-between items-start">
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-2">
                  <FileText size={16} className="text-blue-500" />
                  <span className="font-semibold">{record.type}</span>
                  <span className="text-gray-500 text-sm">• {record.date}</span>
                </div>
                <p className="text-gray-800 mb-1">{record.description}</p>
                <p className="text-gray-600 text-sm">Recorded by {record.doctor}</p>
              </div>
              <button className="text-blue-500 hover:bg-blue-50 p-2 rounded-lg">
                <Download size={16} />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );

  const TelemedicineTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Telemedicine</h2>
      
      <div className="bg-white p-6 rounded-xl shadow-sm border">
        <div className="text-center">
          <Video size={48} className="text-blue-500 mx-auto mb-4" />
          <h3 className="text-lg font-semibold mb-2">No Active Sessions</h3>
          <p className="text-gray-600 mb-4">Your next telemedicine appointment will appear here 15 minutes before the scheduled time.</p>
          <div className="bg-blue-50 p-4 rounded-lg">
            <p className="text-blue-800"><strong>Next Session:</strong> March 25, 2024 at 2:30 PM with Dr. Brown</p>
          </div>
        </div>
      </div>
    </div>
  );

  const PrescriptionsTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Prescriptions</h2>
      
      <div className="grid gap-4">
        {patientData.prescriptions.map((rx) => (
          <div key={rx.id} className="bg-white p-4 rounded-xl shadow-sm border">
            <div className="flex justify-between items-start">
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-2">
                  <Pill size={16} className="text-green-500" />
                  <span className="font-semibold">{rx.medication}</span>
                </div>
                <p className="text-gray-600 mb-1">{rx.pharmacy}</p>
                <p className="text-gray-500 text-sm">Refills remaining: {rx.refillsLeft}</p>
              </div>
              <div className="flex flex-col items-end gap-2">
                <span className={`px-3 py-1 rounded-full text-sm ${
                  rx.status === 'Active' 
                    ? 'bg-green-100 text-green-700' 
                    : 'bg-blue-100 text-blue-700'
                }`}>
                  {rx.status}
                </span>
                <button className="text-blue-500 hover:bg-blue-50 p-2 rounded-lg">
                  <Download size={16} />
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );

  const NotificationsTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Notifications & Reminders</h2>
      
      <div className="space-y-4">
        <div className="bg-white p-4 rounded-xl shadow-sm border border-blue-200">
          <div className="flex items-start gap-3">
            <div className="bg-blue-100 p-2 rounded-full">
              <Calendar size={16} className="text-blue-600" />
            </div>
            <div className="flex-1">
              <h4 className="font-medium">Appointment Reminder</h4>
              <p className="text-gray-600 text-sm">Your appointment with Dr. Smith is tomorrow at 10:00 AM</p>
              <p className="text-gray-400 text-xs mt-1">2 hours ago</p>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-4 rounded-xl shadow-sm border border-green-200">
          <div className="flex items-start gap-3">
            <div className="bg-green-100 p-2 rounded-full">
              <Pill size={16} className="text-green-600" />
            </div>
            <div className="flex-1">
              <h4 className="font-medium">Prescription Ready</h4>
              <p className="text-gray-600 text-sm">Your Vitamin D3 prescription is ready for pickup at City Pharmacy</p>
              <p className="text-gray-400 text-xs mt-1">5 hours ago</p>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-4 rounded-xl shadow-sm border">
          <div className="flex items-start gap-3">
            <div className="bg-purple-100 p-2 rounded-full">
              <FileText size={16} className="text-purple-600" />
            </div>
            <div className="flex-1">
              <h4 className="font-medium">Lab Results Available</h4>
              <p className="text-gray-600 text-sm">New lab results from your recent blood work are now available</p>
              <p className="text-gray-400 text-xs mt-1">1 day ago</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

  const BillingTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Billing & Payments</h2>
      
      <div className="grid gap-4">
        <div className="bg-white p-4 rounded-xl shadow-sm border">
          <div className="flex justify-between items-center">
            <div>
              <h4 className="font-medium">Annual Checkup - Dr. Smith</h4>
              <p className="text-gray-600 text-sm">February 15, 2024</p>
            </div>
            <div className="text-right">
              <p className="font-semibold text-green-600">Paid</p>
              <p className="text-gray-600">$250.00</p>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-4 rounded-xl shadow-sm border border-yellow-200">
          <div className="flex justify-between items-center">
            <div>
              <h4 className="font-medium">Lab Work - Blood Panel</h4>
              <p className="text-gray-600 text-sm">February 10, 2024</p>
            </div>
            <div className="text-right">
              <p className="font-semibold text-yellow-600">Pending</p>
              <p className="text-gray-600">$180.00</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

  const HealthTipsTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Health Tips & Resources</h2>
      
      <div className="grid md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3">Managing Hypertension</h3>
          <p className="text-gray-600 text-sm mb-4">Learn about lifestyle changes that can help control your blood pressure naturally.</p>
          <button className="text-blue-500 text-sm hover:underline">Read More →</button>
        </div>
        
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3">Heart-Healthy Diet</h3>
          <p className="text-gray-600 text-sm mb-4">Discover foods that support cardiovascular health and overall wellness.</p>
          <button className="text-blue-500 text-sm hover:underline">Read More →</button>
        </div>
        
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3">Exercise Guidelines</h3>
          <p className="text-gray-600 text-sm mb-4">Safe and effective exercise routines for maintaining your health.</p>
          <button className="text-blue-500 text-sm hover:underline">Read More →</button>
        </div>
        
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3">Medication Adherence</h3>
          <p className="text-gray-600 text-sm mb-4">Tips for remembering to take your medications consistently.</p>
          <button className="text-blue-500 text-sm hover:underline">Read More →</button>
        </div>
      </div>
    </div>
  );

  const SecurityTab = () => (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold">Security & Account</h2>
      
      <div className="space-y-4">
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3 flex items-center gap-2">
            <Shield className="text-green-500" size={20} />
            Multi-Factor Authentication
          </h3>
          <p className="text-gray-600 text-sm mb-4">Add an extra layer of security to your account</p>
          <button className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600">
            Enable MFA
          </button>
        </div>
        
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3">Password</h3>
          <p className="text-gray-600 text-sm mb-4">Change your account password</p>
          <button className="border border-gray-300 px-4 py-2 rounded-lg hover:bg-gray-50">
            Change Password
          </button>
        </div>
        
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <h3 className="font-semibold mb-3">Privacy Settings</h3>
          <p className="text-gray-600 text-sm mb-4">Manage how your data is used and shared</p>
          <button className="border border-gray-300 px-4 py-2 rounded-lg hover:bg-gray-50">
            Review Settings
          </button>
        </div>
        
        <div className="pt-4">
          <button className="bg-red-500 text-white px-6 py-2 rounded-lg hover:bg-red-600">
            Logout
          </button>
        </div>
      </div>
    </div>
  );

  const renderActiveTab = () => {
    switch(activeTab) {
      case 'profile': return <ProfileTab />;
      case 'appointments': return <AppointmentsTab />;
      case 'ehr': return <EHRTab />;
      case 'telemedicine': return <TelemedicineTab />;
      case 'prescriptions': return <PrescriptionsTab />;
      case 'notifications': return <NotificationsTab />;
      case 'billing': return <BillingTab />;
      case 'health-tips': return <HealthTipsTab />;
      case 'security': return <SecurityTab />;
      default: return <ProfileTab />;
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 py-4">
          <div className="flex justify-between items-center">
            <h1 className="text-xl font-bold text-gray-800">Patient Portal</h1>
            <div className="flex items-center gap-4">
              <div className="relative">
                <button 
                  onClick={() => setShowNotifications(!showNotifications)}
                  className="relative p-2 text-gray-600 hover:bg-gray-100 rounded-lg"
                >
                  <Bell size={20} />
                  {notifications > 0 && (
                    <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full px-1.5 py-0.5 min-w-5 text-center">
                      {notifications}
                    </span>
                  )}
                </button>
                
                {showNotifications && (
                  <div className="absolute right-0 mt-2 w-80 bg-white rounded-lg shadow-lg border z-50">
                    <div className="p-4 border-b">
                      <h3 className="font-semibold">Recent Notifications</h3>
                    </div>
                    <div className="p-2 max-h-64 overflow-y-auto">
                      <div className="p-3 hover:bg-gray-50 rounded-lg cursor-pointer">
                        <p className="text-sm font-medium">Appointment Reminder</p>
                        <p className="text-xs text-gray-600">Tomorrow at 10:00 AM</p>
                      </div>
                      <div className="p-3 hover:bg-gray-50 rounded-lg cursor-pointer">
                        <p className="text-sm font-medium">Prescription Ready</p>
                        <p className="text-xs text-gray-600">Vitamin D3 - City Pharmacy</p>
                      </div>
                    </div>
                  </div>
                )}
              </div>
              <div className="flex items-center gap-2">
                <div className="w-8 h-8 bg-blue-500 rounded-full flex items-center justify-center text-white text-sm font-semibold">
                  {patientData.name.split(' ').map(n => n[0]).join('')}
                </div>
                <span className="hidden md:inline text-gray-700">{patientData.name}</span>
              </div>
            </div>
          </div>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 py-6">
        <div className="flex flex-col lg:flex-row gap-6">
          {/* Sidebar Navigation */}
          <nav className="lg:w-64 space-y-2">
            <div className="bg-white p-4 rounded-xl shadow-sm">
              <div className="grid grid-cols-3 lg:grid-cols-1 gap-2">
                <TabButton id="profile" icon={User} label="Profile" />
                <TabButton id="appointments" icon={Calendar} label="Appointments" />
                <TabButton id="ehr" icon={FileText} label="Health Records" />
                <TabButton id="telemedicine" icon={Video} label="Telemedicine" />
                <TabButton id="prescriptions" icon={Pill} label="Prescriptions" />
                <TabButton id="notifications" icon={Bell} label="Notifications" count={notifications} />
                <TabButton id="billing" icon={CreditCard} label="Billing" />
                <TabButton id="health-tips" icon={BookOpen} label="Health Tips" />
                <TabButton id="security" icon={Shield} label="Security" />
              </div>
            </div>
          </nav>

          {/* Main Content */}
          <main className="flex-1">
            {renderActiveTab()}
          </main>
        </div>
      </div>
    </div>
  );
};

export default PatientPortal;
