document.getElementById("signup-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const role = document.getElementById("role").value;
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const phoneNumber = document.getElementById("phoneNumber").value;
    const password = document.getElementById("password").value;
    const confirmPass = document.getElementById("confirmPass").value;
    const errorMessage = document.getElementById("error-message");

    // Clear previous error messages
    errorMessage.textContent = "";
    errorMessage.classList.add("d-none");

    // Validate role selection
    if (!role) {
        errorMessage.textContent = "Please select a role";
        errorMessage.classList.remove("d-none");
        return;
    }

    // Validate password match
    if (password !== confirmPass) {
        errorMessage.textContent = "Passwords do not match";
        errorMessage.classList.remove("d-none");
        return;
    }

    try {
        // Get the base URL from the environment or use a default
        const baseUrl = "http://localhost:8081/api/v1";

        // Determine the signup endpoint based on the selected role
        let signupEndpoint;
        switch (role) {
            case "CUSTOMER":
                signupEndpoint = `${baseUrl}/users/signup`;
                break;
            case "OWNER":
                signupEndpoint = `${baseUrl}/Salon_Admin/signup`;
                break;
            case "ADMIN":
                signupEndpoint = `${baseUrl}/General_Admin/signup`;
                break;
            default:
                throw new Error("Invalid role selected");
        }

        const response = await fetch(signupEndpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                username: username,
                email: email,
                password: password,
                phoneNumber: phoneNumber
            }),
        });

        console.log("Raw response:", response); // Log raw response
        if (!response.ok) {
            const errorText = await response.text();
            console.error("Error response text:", errorText); // Log error details
            throw new Error(errorText || "Registration failed. Please try again.");
        }

        const data = await response.json();


        // Show success message
        alert("Registration successful! Please login to continue.");

        // Redirect to login page
        window.location.href = "Login.html";
    } catch (error) {
        console.error("Error during registration:", error);
        errorMessage.textContent = error.message || "Registration failed. Please try again.";
        errorMessage.classList.remove("d-none");
    }
});
