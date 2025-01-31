// Get token from localStorage
const token = localStorage.getItem('token');

if (!token) {
    window.location.href = '../login.html';
}

// Function to fetch salon admin profile
async function fetchSalonAdminProfile() {
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
        document.getElementById('adminName').textContent = userData.firstName || 'Salon Admin';
    } catch (error) {
        console.error('Error fetching profile:', error);
        document.getElementById('adminName').textContent = 'Salon Admin';
    }
}

// Function to create a new salon
async function createSalon(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const salonData = {
        name: formData.get('name'),
        description: formData.get('description'),
        address: formData.get('address'),
        phoneNumber: formData.get('phoneNumber'),
        openingHours: formData.get('openingHours'),
        image: formData.get('image')
    };

    try {
        const response = await fetch('http://localhost:8081/api/v1/salons', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(salonData)
        });

        if (!response.ok) {
            throw new Error('Failed to create salon');
        }

        const result = await response.text();
        alert(result);
        document.getElementById('createSalonForm').reset();
    } catch (error) {
        console.error('Error creating salon:', error);
        alert('Failed to create salon. Please try again.');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    fetchSalonAdminProfile();

    // Add form submit handler
    const createSalonForm = document.getElementById('createSalonForm');
    if (createSalonForm) {
        createSalonForm.addEventListener('submit', createSalon);
    }
});

// Handle logout
document.getElementById('logout').addEventListener('click', (e) => {
    e.preventDefault();
    localStorage.removeItem('token');
    window.location.href = '../login.html';
});
