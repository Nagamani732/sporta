import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

// Access the Environment Variable
const apiClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
});

const App = () => {
    const [currentPage, setCurrentPage] = useState("home");
    const [members, setMembers] = useState([]);

    // Fetch members when navigating to "listMembers"
    useEffect(() => {
        if (currentPage === "listMembers") {
            fetchMembers();
        }
    }, [currentPage]);

    const fetchMembers = async () => {
        try {
            const response = await apiClient.get("/members");
            setMembers(response.data);
        } catch (error) {
            console.error("Error fetching members:", error);
        }
    };

    const handleNavigation = (page) => {
        setCurrentPage(page);
    };

    return (
        <div className="app-container">
            <div className="toolbar">Sporta Membership Management</div>
            <div className="content-container">
                <div className="sidebar">
                    <h2>Menu</h2>
                    <nav>
                        <a
                            href="#home"
                            className={currentPage === "home" ? "active" : ""}
                            onClick={() => handleNavigation("home")}
                        >
                            Home
                        </a>
                        <a
                            href="#addMember"
                            className={currentPage === "addMember" ? "active" : ""}
                            onClick={() => handleNavigation("addMember")}
                        >
                            Add Member
                        </a>
                        <a
                            href="#listMembers"
                            className={currentPage === "listMembers" ? "active" : ""}
                            onClick={() => handleNavigation("listMembers")}
                        >
                            List Members
                        </a>
                    </nav>
                </div>
                <div className="main-content">
                    {currentPage === "home" && <HomePage />}
                    {currentPage === "addMember" && <AddMemberPage />}
                    {currentPage === "listMembers" && (
                        <ListMembersPage members={members} />
                    )}
                </div>
            </div>
        </div>
    );
};

const HomePage = () => (
    <div>
        <h2>Welcome to Sporta!</h2>
        <p>
            Manage your sports club members efficiently. Use the navigation menu to
            add members or view the list of current members.
        </p>
    </div>
);

const AddMemberPage = () => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [joinDate, setJoinDate] = useState("");

    const handleAddMember = async (e) => {
        e.preventDefault();
        try {
            const newMember = { name, email, phoneNumber, joinDate };
            await apiClient.post("/members", newMember);
            alert("Member added successfully!");
            setName("");
            setEmail("");
            setPhoneNumber("");
            setJoinDate("");
        } catch (error) {
            console.error("Error adding member:", error);
        }
    };

    return (
        <div>
            <h2>Add Member</h2>
            <form onSubmit={handleAddMember}>
                <label>Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    placeholder="Enter Name"
                />
                <label>Email:</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    placeholder="Enter Email"
                />
                <label>Phone Number:</label>
                <input
                    type="tel"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    required
                    placeholder="Enter Phone Number"
                />
                <label>Join Date:</label>
                <input
                    type="date"
                    value={joinDate}
                    onChange={(e) => setJoinDate(e.target.value)}
                    required
                />
                <button type="submit">Add Member</button>
            </form>
        </div>
    );
};

const ListMembersPage = ({ members }) => (
    <div>
        <h2>List of Members</h2>
        {members.length === 0 ? (
            <p>No members found. Add some members to see them here.</p>
        ) : (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Join Date</th>
                </tr>
                </thead>
                <tbody>
                {members.map((member) => (
                    <tr key={member.id}>
                        <td>{member.name}</td>
                        <td>{member.email}</td>
                        <td>{member.phoneNumber}</td>
                        <td>{member.joinDate}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        )}
    </div>
);

export default App;
