document.addEventListener('DOMContentLoaded', async function() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '../Login.html';
        return;
    }

    try {
        // Fetch owner details
        const userResponse = await fetch('http://localhost:8081/api/v1/users/profile', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!userResponse.ok) {
            throw new Error('Failed to fetch user details');
        }

        const userData = await userResponse.json();
        document.getElementById('ownerName').textContent = userData.firstName;

        // Check if owner has a salon
        const salonResponse = await fetch('http://localhost:8081/api/v1/salons/owner', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (salonResponse.ok) {
            const salonData = await salonResponse.json();
            displaySalonStatus(salonData);
            if (salonData.authorized) {
                document.getElementById('createSalonSection').style.display = 'none';
                document.getElementById('manageSalonSection').style.display = 'block';
                loadServices(salonData.id);
            }
        } else if (salonResponse.status === 404) {
            displayCreateSalonForm();
        } else {
            throw new Error('Failed to fetch salon details');
        }

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to load dashboard data');
    }

    // Setup logout handler
    document.getElementById('logout').addEventListener('click', function(e) {
        e.preventDefault();
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
        window.location.href = '../Login.html';
    });

    // Setup salon form submission
    document.getElementById('salonForm').addEventListener('submit', handleSalonSubmission);
});

function displaySalonStatus(salon) {
    const statusDiv = document.getElementById('salonStatus');
    let statusHtml = `
        <h4>${salon.name}</h4>
        <p><strong>Status:</strong> 
            <span class="status-${salon.authorized ? 'approved' : 'pending'}">
                ${salon.authorized ? 'Approved' : 'Pending Approval'}
            </span>
        </p>
    `;

    if (!salon.authorized) {
        statusHtml += `
            <p class="text-muted">
                Your salon is pending approval from the administrator. 
                You'll be notified via email once it's approved.
            </p>
        `;
    }

    statusDiv.innerHTML = statusHtml;
}

function displayCreateSalonForm() {
    document.getElementById('createSalonSection').style.display = 'block';
    document.getElementById('manageSalonSection').style.display = 'none';
}

async function handleSalonSubmission(e) {
    e.preventDefault();
    const token = localStorage.getItem('token');

    const salonData = {
        name: document.getElementById('salonName').value,
        description: document.getElementById('description').value,
        location: document.getElementById('location').value,
        contact: document.getElementById('contact').value,
        openingHours: document.getElementById('openingHours').value,
        imageUrl: document.getElementById('imageUrl').value || null
    };

    try {
        const response = await fetch('http://localhost:8081/api/v1/salons', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(salonData)
        });

        if (!response.ok) {
            throw new Error('Failed to create salon');
        }

        const salon = await response.json();
        alert('Salon created successfully! Waiting for admin approval.');
        location.reload();

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to create salon. Please try again.');
    }
}

async function loadServices(salonId) {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`http://localhost:8081/api/v1/salons/${salonId}/services`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch services');
        }

        const services = await response.json();
        displayServices(services);

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to load services');
    }
}

function displayServices(services) {
    const servicesList = document.getElementById('servicesList');
    servicesList.innerHTML = services.length === 0 ?
        '<p class="text-muted">No services added yet.</p>' : '';

    services.forEach(service => {
        const serviceElement = document.createElement('div');
        serviceElement.className = 'service-item';
        serviceElement.innerHTML = `
            <div class="service-info">
                <h5>${service.name}</h5>
                <p class="service-price">$${service.price.toFixed(2)}</p>
                <p class="service-duration">${service.duration} minutes</p>
            </div>
            <div class="service-actions">
                <button class="btn btn-sm btn-primary" onclick="editService('${service.id}')">Edit</button>
                <button class="btn btn-sm btn-danger" onclick="deleteService('${service.id}')">Delete</button>
            </div>
        `;
        servicesList.appendChild(serviceElement);
    });
}

function openAddServiceModal() {
    const modal = new bootstrap.Modal(document.getElementById('serviceModal'));
    document.getElementById('serviceForm').reset();
    document.getElementById('serviceForm').onsubmit = handleAddService;
    modal.show();
}

async function handleAddService(e) {
    e.preventDefault();
    const token = localStorage.getItem('token');

    const serviceData = {
        name: document.getElementById('serviceName').value,
        description: document.getElementById('serviceDescription').value,
        price: parseFloat(document.getElementById('servicePrice').value),
        duration: parseInt(document.getElementById('serviceDuration').value)
    };

    try {
        const response = await fetch('http://localhost:8081/api/v1/services', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(serviceData)
        });

        if (!response.ok) {
            throw new Error('Failed to add service');
        }

        bootstrap.Modal.getInstance(document.getElementById('serviceModal')).hide();
        location.reload();

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to add service. Please try again.');
    }
}

async function deleteService(serviceId) {
    if (!confirm('Are you sure you want to delete this service?')) {
        return;
    }

    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`http://localhost:8081/api/v1/services/${serviceId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Failed to delete service');
        }

        location.reload();

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to delete service. Please try again.');
    }
}
