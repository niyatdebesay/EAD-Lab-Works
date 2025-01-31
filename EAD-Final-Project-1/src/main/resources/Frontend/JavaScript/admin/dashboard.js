// Get token from localStorage
const token = localStorage.getItem('token');

if (!token) {
    window.location.href = '../login.html';
}

// Function to fetch admin profile
async function fetchAdminProfile() {
    try {
        const response = await fetch('http://localhost:8081/api/v1/users/profile', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch profile');
        }

        const userData = await response.json();
        document.getElementById('adminName').textContent = userData.firstName || 'Admin';
    } catch (error) {
        console.error('Error fetching profile:', error);
        document.getElementById('adminName').textContent = 'Admin';
    }
}

// Function to fetch pending salons
async function fetchPendingSalons() {
    try {
        const response = await fetch('http://localhost:8081/api/v1/salons/pending', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch pending salons');
        }

        const salons = await response.json();
        displayPendingSalons(salons);
    } catch (error) {
        console.error('Error loading pending salons:', error);
        document.getElementById('pendingSalons').innerHTML = `
            <div class="alert alert-danger">
                Error loading pending salons. Please try again later.
            </div>
        `;
    }
}

// Function to display pending salons
function displayPendingSalons(salons) {
    const pendingSalonsList = document.getElementById('pendingSalons');

    if (!salons || salons.length === 0) {
        pendingSalonsList.innerHTML = `
            <div class="alert alert-info">
                No pending salons to review at the moment.
            </div>
        `;
        return;
    }

    pendingSalonsList.innerHTML = salons.map(salon => `
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${salon.name}</h5>
                <p class="card-text">${salon.description}</p>
                <p><strong>Address:</strong> ${salon.address}</p>
                <p><strong>Phone:</strong> ${salon.phoneNumber}</p>
                <p><strong>Hours:</strong> ${salon.openingHours}</p>
                <div class="btn-group">
                    <button class="btn btn-success" onclick="authorizeSalon('${salon.id}')">
                        <i class="fas fa-check"></i> Authorize
                    </button>
                    <button class="btn btn-danger" onclick="rejectSalon('${salon.id}')">
                        <i class="fas fa-times"></i> Reject
                    </button>
                </div>
            </div>
        </div>
    `).join('');
}

// Function to authorize a salon
async function authorizeSalon(salonId) {
    try {
        const response = await fetch(`http://localhost:8081/api/v1/salons/${salonId}/authorize`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to authorize salon');
        }

        alert('Salon authorized successfully');
        fetchPendingSalons(); // Refresh the list
    } catch (error) {
        console.error('Error authorizing salon:', error);
        alert('Failed to authorize salon. Please try again.');
    }
}

// Function to reject a salon
async function rejectSalon(salonId) {
    try {
        const response = await fetch(`http://localhost:8081/api/v1/salons/${salonId}/reject`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to reject salon');
        }

        alert('Salon rejected successfully');
        fetchPendingSalons(); // Refresh the list
    } catch (error) {
        console.error('Error rejecting salon:', error);
        alert('Failed to reject salon. Please try again.');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    fetchAdminProfile();
    fetchPendingSalons();
});

// Handle logout
document.getElementById('logout').addEventListener('click', (e) => {
    e.preventDefault();
    localStorage.removeItem('token');
    window.location.href = '../login.html';
});
