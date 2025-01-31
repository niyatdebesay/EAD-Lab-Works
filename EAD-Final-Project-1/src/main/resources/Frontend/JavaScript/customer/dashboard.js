// Get token from localStorage
const token = localStorage.getItem('token');

if (!token) {
    window.location.href = '../login.html';
}

// Function to fetch user profile
async function fetchUserProfile() {
    try {
        const response = await fetch('http://localhost:8081/api/v1/users/profile', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch user profile');
        }

        const userData = await response.json();
        document.getElementById('userFirstName').textContent = userData.firstName || 'User';
    } catch (error) {
        console.error('Error fetching user profile:', error);
        document.getElementById('userFirstName').textContent = 'User';
    }
}

// Function to fetch authorized salons
async function fetchAuthorizedSalons() {
    try {
        const response = await fetch('http://localhost:8081/api/v1/salons/authorized', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch salons');
        }

        const salons = await response.json();
        const salonList = document.getElementById('salonList');

        // Remove loading placeholder
        const loadingPlaceholder = document.getElementById('loadingPlaceholder');
        if (loadingPlaceholder) {
            loadingPlaceholder.remove();
        }

        if (salons.length === 0) {
            salonList.innerHTML = `
                <div class="col-12 text-center py-5">
                    <i class="fas fa-info-circle fa-2x text-muted mb-3"></i>
                    <p class="lead text-muted">No salons available at the moment.</p>
                </div>
            `;
            return;
        }

        salonList.innerHTML = salons.map(salon => `
            <div class="col-md-4 mb-4">
                <div class="salon-card">
                    <img src="${salon.image || '../assets/default-salon.jpg'}" class="salon-image" alt="${salon.name}">
                    <div class="card-body">
                        <h5 class="card-title">${salon.name}</h5>
                        <p class="card-text">${salon.description || 'No description available'}</p>
                        <p class="mb-2"><i class="fas fa-map-marker-alt me-2"></i>${salon.address}</p>
                        <p class="mb-2"><i class="fas fa-phone me-2"></i>${salon.phoneNumber}</p>
                        <p class="mb-3"><i class="fas fa-clock me-2"></i>${salon.openingHours}</p>
                        <a href="#" class="btn btn-visit w-100">Visit Salon</a>
                    </div>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading salons:', error);
        document.getElementById('salonList').innerHTML = `
            <div class="col-12 text-center py-5">
                <i class="fas fa-exclamation-circle fa-2x text-danger mb-3"></i>
                <p class="lead text-danger">Error loading salons. Please try again later.</p>
            </div>
        `;
    }
}

// Handle logout
document.getElementById('logout').addEventListener('click', (e) => {
    e.preventDefault();
    localStorage.removeItem('token');
    window.location.href = '../login.html';
});

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    fetchUserProfile();
    fetchAuthorizedSalons();
});
