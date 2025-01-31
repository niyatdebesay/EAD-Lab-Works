document.addEventListener('DOMContentLoaded', async function() {
    const token = localStorage.getItem('token');
    const salonId = localStorage.getItem('selectedSalonId');

    if (!token || !salonId) {
        window.location.href = '../Login.html';
        return;
    }

    try {
        // Fetch salon details
        const salonResponse = await fetch(`http://localhost:8081/api/v1/salons/${salonId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!salonResponse.ok) {
            throw new Error('Failed to fetch salon details');
        }

        const salon = await salonResponse.json();
        displaySalonDetails(salon);

        // Fetch salon services
        const servicesResponse = await fetch(`http://localhost:8081/api/v1/salons/${salonId}/services`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!servicesResponse.ok) {
            throw new Error('Failed to fetch services');
        }

        const services = await servicesResponse.json();
        displayServices(services);

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to load salon data');
    }

    // Setup logout handler
    document.getElementById('logout').addEventListener('click', function(e) {
        e.preventDefault();
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
        localStorage.removeItem('selectedSalonId');
        window.location.href = '../Login.html';
    });
});

function displaySalonDetails(salon) {
    document.getElementById('salonName').textContent = salon.name;
    document.getElementById('salonDescription').textContent = salon.description;
    document.getElementById('openingHours').textContent = salon.openingHours;
    document.getElementById('location').textContent = salon.location;
    document.getElementById('contact').textContent = salon.contact;

    if (salon.imageUrl) {
        document.getElementById('salonImage').src = salon.imageUrl;
    }
}

function displayServices(services) {
    const servicesList = document.getElementById('servicesList');
    servicesList.innerHTML = '';

    services.forEach(service => {
        const serviceCard = document.createElement('div');
        serviceCard.className = 'col-md-4 mb-4';
        serviceCard.innerHTML = `
            <div class="service-card">
                <div class="card-body">
                    <h5 class="card-title">${service.name}</h5>
                    <div class="service-price">$${service.price.toFixed(2)}</div>
                    <p class="service-description">${service.description}</p>
                    <button class="btn btn-book" onclick="openBookingModal('${service.id}', '${service.name}', ${service.price})">
                        Book Now
                    </button>
                </div>
            </div>
        `;
        servicesList.appendChild(serviceCard);
    });
}

function openBookingModal(serviceId, serviceName, servicePrice) {
    document.getElementById('serviceName').value = serviceName;

    // Set minimum date to today
    const today = new Date().toISOString().split('T')[0];
    const appointmentDate = document.getElementById('appointmentDate');
    appointmentDate.min = today;

    // Populate time slots
    const timeSelect = document.getElementById('appointmentTime');
    timeSelect.innerHTML = '';

    // Add time slots from 9 AM to 5 PM
    for (let hour = 9; hour <= 17; hour++) {
        const time = `${hour.toString().padStart(2, '0')}:00`;
        const option = document.createElement('option');
        option.value = time;
        option.textContent = time;
        timeSelect.appendChild(option);
    }

    // Show the modal
    const bookingModal = new bootstrap.Modal(document.getElementById('bookingModal'));
    bookingModal.show();

    // Handle form submission
    document.getElementById('bookingForm').onsubmit = async function(e) {
        e.preventDefault();

        const token = localStorage.getItem('token');
        const salonId = localStorage.getItem('selectedSalonId');

        try {
            const response = await fetch('http://localhost:8081/api/v1/appointments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    salonId: salonId,
                    serviceId: serviceId,
                    appointmentDate: document.getElementById('appointmentDate').value,
                    appointmentTime: document.getElementById('appointmentTime').value,
                    specialRequests: document.getElementById('specialRequests').value
                })
            });

            if (!response.ok) {
                throw new Error('Failed to book appointment');
            }

            alert('Appointment booked successfully!');
            bookingModal.hide();
            window.location.href = 'appointments.html';

        } catch (error) {
            console.error('Error:', error);
            alert('Failed to book appointment. Please try again.');
        }
    };
}
