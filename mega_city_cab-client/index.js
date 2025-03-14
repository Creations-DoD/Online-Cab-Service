
document.addEventListener('DOMContentLoaded', function () {
            
    // Restrict Past Dates in Date-Time Picker
    let now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    document.querySelectorAll('#datetime-local').forEach(input => {
        input.min = now.toISOString().slice(0, 16);
    });

    const signInButton = document.querySelector('.sign-in-btn');
    const loginPopup = document.querySelector('.login-popup');
    const signupPopup = document.querySelector('.signup-popup');
    const closeBtns = document.querySelectorAll('.close-btn');
    const blurWrapper = document.querySelector('.blur-wrapper');
    const registerLink = document.querySelector('.register-link');
    const signInLink = document.querySelector('.sign-in-link');
    const logoutButton = document.querySelector('.dropdown-content a[href="#logout"]');

    const toggleScrollLock = (lock) => {
        if (lock) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = '';
        }
    };

    signInButton.addEventListener('click', function () {
        loginPopup.querySelector('input[type="text"]').value = '';
        loginPopup.querySelector('input[type="password"]').value = '';
        loginPopup.querySelector('input[type="checkbox"]').checked = false;
        document.getElementById("message_Login").textContent = ''; // Clear message_Login
        loginPopup.classList.add('active');
        blurWrapper.classList.add('blur-background');
        toggleScrollLock(true);
    });

    closeBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            loginPopup.classList.remove('active');
            signupPopup.classList.remove('active');
            blurWrapper.classList.remove('blur-background');
            toggleScrollLock(false);
        });
    });

    registerLink.addEventListener('click', function () {
        loginPopup.classList.remove('active');
        signupPopup.querySelectorAll('input').forEach(input => input.value = '');
        signupPopup.querySelector('input[type="checkbox"]').checked = false;
        document.getElementById("message_CusReg").textContent = ''; // Clear message_CusReg
        signupPopup.classList.add('active');
    });

    signInLink.addEventListener('click', function () {
        signupPopup.classList.remove('active');
        loginPopup.querySelectorAll('input').forEach(input => input.value = '');
        loginPopup.querySelector('input[type="checkbox"]').checked = false;
        document.getElementById("message_Login").textContent = ''; // Clear message_Login
        loginPopup.classList.add('active');
    });

    logoutButton.addEventListener('click', function () {
        document.querySelector('.sign-in-btn').style.display = 'block';
        // document.getElementById('txtUser').classList.add('hidden');
        const user = document.getElementById('txtUser');
        user.textContent = '';
        user.value = '';
        user.classList.add('hidden');
    });

    

    // Fetch cities and populate dropdowns
    const cityUrl = "http://192.168.1.100:8080/mega_city_cab-services/city";
    const pickupCityDropdowns = document.querySelectorAll('#pickup-city-dropdown');
    const dropCityDropdowns = document.querySelectorAll('#drop-city-dropdown');

    fetch(cityUrl)
        .then(response => response.json())
        .then(data => {
            data.forEach(city => {
                pickupCityDropdowns.forEach(dropdown => {
                    const option = document.createElement('option');
                    option.value = city.left;
                    option.textContent = city.right;
                    option.classList.add('bg-black');
                    dropdown.appendChild(option);
                });

                dropCityDropdowns.forEach(dropdown => {
                    const option = document.createElement('option');
                    option.value = city.left;
                    option.textContent = city.right;
                    option.classList.add('bg-black');
                    dropdown.appendChild(option);
                });
            });

            // Set default select option value for 'Airport Pickup' tab
            pickupCityDropdowns.forEach(dropdown => {
                if (dropdown.closest('#tab-airport-pickup')) {
                    Array.from(dropdown.options).forEach(option => {
                        if (option.textContent === "Bandaranaike International Airport") {
                            option.selected = true;
                        }
                    });
                    // Remove "Bandaranaike International Airport" from the drop location dropdown
                    const dropCityDropdown = dropdown.closest('#tab-airport-pickup').querySelector('#drop-city-dropdown');
                    Array.from(dropCityDropdown.options).forEach(option => {
                        if (option.textContent === "Bandaranaike International Airport") {
                            option.style.display = 'none';
                        }
                    });
                }
            });

            // Set default select option value for 'Airport Drop' tab
            dropCityDropdowns.forEach(dropdown => {
                if (dropdown.closest('#tab-airport-drop')) {
                    Array.from(dropdown.options).forEach(option => {
                        if (option.textContent === "Bandaranaike International Airport") {
                            option.selected = true;
                        }
                    });
                    // Remove "Bandaranaike International Airport" from the pick-up location dropdown
                    const pickupCityDropdown = dropdown.closest('#tab-airport-drop').querySelector('#pickup-city-dropdown');
                    Array.from(pickupCityDropdown.options).forEach(option => {
                        if (option.textContent === "Bandaranaike International Airport") {
                            option.style.display = 'none';
                        }
                    });
                }
            });
        })
        .catch(error => {
        });

    const tabs = document.querySelectorAll('.tab-link');
    const contents = document.querySelectorAll('.tab-content');
    const nestedTabs = document.querySelectorAll('.nested-tab-link');
    const vehicle_model_url = "http://192.168.1.100:8080/mega_city_cab-services/vehicle/vehicle-model/";

    const tripCostUrl = "http://192.168.1.100:8080/mega_city_cab-services/tripCost";

    function updateTripCost(pickupValue, dropValue, vehicleType) {
        if (pickupValue && dropValue && vehicleType) {
            const tripData = {
                pickup: pickupValue,
                drop: dropValue,
                vehicleType: vehicleType
            };

            const options = {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify(tripData)
            };

            fetch(tripCostUrl, options)
                .then(res => res.json())
                .then(data => {
                    document.querySelectorAll('#trip-cost').forEach(costElement => {
                        costElement.value = data.cityDistanceId;
                        costElement.textContent = `Est LKR ${data.tripCost}`;
                    });
                })
                .catch(error => {
                    console.error('Error fetching trip cost:', error);
                });
        }
    }

    function handleDropdownChange(section) {
        const pickupCityDropdown = section.querySelector('#pickup-city-dropdown');
        const dropCityDropdown = section.querySelector('#drop-city-dropdown');
        const activeNestedTab = section.querySelector('.nested-tab-link.active-tab');

        const pickupValue = pickupCityDropdown.value;
        const dropValue = dropCityDropdown.value;
        const vehicleType = activeNestedTab ? activeNestedTab.textContent.trim() : null;

        updateTripCost(pickupValue, dropValue, vehicleType);
    }

    function addDropdownEventListeners(section) {
        const pickupCityDropdown = section.querySelector('#pickup-city-dropdown');
        const dropCityDropdown = section.querySelector('#drop-city-dropdown');

        pickupCityDropdown.addEventListener('change', function () {
            updateDropdownOptions(section, pickupCityDropdown, dropCityDropdown);
            handleDropdownChange(section);
        });

        dropCityDropdown.addEventListener('change', function () {
            updateDropdownOptions(section, dropCityDropdown, pickupCityDropdown);
            handleDropdownChange(section);
        });
    }

    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
            tabs.forEach(t => t.classList.remove('active-tab'));
            contents.forEach(c => c.classList.remove('active'));

            this.classList.add('active-tab');
            const targetContent = document.querySelector(this.getAttribute('data-target'));
            targetContent.classList.add('active');

            // Automatically click the first nested tab
            const firstNestedTab = targetContent.querySelector('.nested-tab-link');
            if (firstNestedTab) {
                firstNestedTab.click();
            }
        });
    });

    nestedTabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const parent = this.closest('.tab-content');
            const parentTabs = parent.querySelectorAll('.nested-tab-link');
            const nestedContentAirportPickup = document.getElementById('nested-tab-content-airport-pickup');
            const nestedContentAirportDrop = document.getElementById('nested-tab-content-airport-drop');
            const nestedContentRideNow = document.getElementById('nested-tab-content-ride-now');

            parentTabs.forEach(t => t.classList.remove('active-tab'));
            this.classList.add('active-tab');

            let modelType = this.textContent.trim();
            fetch(vehicle_model_url + modelType)
                .then(res => res.json())
                .then(data => {
                    const formattedPrice = parseFloat(data.price).toFixed(2);
                    if (parent.id === 'tab-airport-pickup') {
                        nestedContentAirportPickup.innerHTML = `
                            <div class="flex gap-5 mt-10 text-lg">
                                <div class="w-2/3 border-2 rounded-lg">
                                    <div class="grid grid-cols-3 gap-10 p-10 ">
                                        <p class="flex justify-center">${data.model}</p>
                                        <p class="flex justify-center">${data.condition}</p>
                                        <p class="flex justify-center">${data.baggagers} baggage</p>
                                        <p class="flex justify-center">Flexible Payment</p>
                                        <p class="flex justify-center">${data.capacity} passengers</p>
                                    </div>
                                </div>
                                <div class="w-1/3 mr-10 border-2 rounded-lg">
                                    <div class="flex justify-center p-16">
                                        <p id="trip-cost" class="">Est LKR 0.00</p>
                                    </div>
                                </div>
                            </div>
                        `;          //  <p id="trip-cost" class="">Est LKR ${formattedPrice} (per km)</p>
                    } else if (parent.id === 'tab-airport-drop') {
                        nestedContentAirportDrop.innerHTML = `
                            <div class="flex gap-5 mt-10 text-lg">
                                <div class="w-2/3 border-2 rounded-lg">
                                    <div class="grid grid-cols-3 gap-10 p-10 ">
                                        <p class="flex justify-center">${data.model}</p>
                                        <p class="flex justify-center">${data.condition}</p>
                                        <p class="flex justify-center">${data.baggagers} baggage</p>
                                        <p class="flex justify-center">Flexible Payment</p>
                                        <p class="flex justify-center">${data.capacity} passengers</p>
                                    </div>
                                </div>
                                <div class="w-1/3 mr-10 border-2 rounded-lg">
                                    <div class="flex justify-center p-16">
                                        <p id="trip-cost" class="">Est LKR 0.00</p>
                                    </div>
                                </div>
                            </div>
                        `;
                    } else if (parent.id === 'tab-ride-now') {
                        nestedContentRideNow.innerHTML = `
                            <div class="flex gap-5 mt-10 text-lg">
                                <div class="w-2/3 border-2 rounded-lg">
                                    <div class="grid grid-cols-3 gap-10 p-10 ">
                                        <p class="flex justify-center">${data.model}</p>
                                        <p class="flex justify-center">${data.condition}</p>
                                        <p class="flex justify-center">${data.baggagers} baggage</p>
                                        <p class="flex justify-center">Flexible Payment</p>
                                        <p class="flex justify-center">${data.capacity} passengers</p>
                                    </div>
                                </div>
                                <div class="w-1/3 mr-10 border-2 rounded-lg">
                                    <div class="flex justify-center p-16">
                                        <p id="trip-cost" class="">Est LKR 0.00</p>
                                    </div>
                                </div>
                            </div>
                        `;
                    }
                    handleDropdownChange(parent);
                })
                .catch(error => {
                    console.error('Error fetching vehicle model data:', error);
                });
        });
    });

    // Automatically select "Airport Pickup" tab and "Budget" nested tab on page load
    document.querySelector('.tab-link[data-target="#tab-airport-pickup"]').click();
    document.querySelector('.nested-tab-link[data-target="#tab-airport-pickup-budget"]').click();

    // Function to update dropdown options within a specific section
    function updateDropdownOptions(section, selectedDropdown, otherDropdown) {
        const selectedValue = selectedDropdown.value;
        const otherOptions = otherDropdown.querySelectorAll('option');

        otherOptions.forEach(option => {
            if (option.value === selectedValue) {
                option.style.display = 'none';
            } else {
                option.style.display = 'block';
            }
        });
    }

    // Event listeners for dropdown changes within each section
    function addDropdownEventListeners(section) {
        const pickupCityDropdown = section.querySelector('#pickup-city-dropdown');
        const dropCityDropdown = section.querySelector('#drop-city-dropdown');

        pickupCityDropdown.addEventListener('change', function () {
            updateDropdownOptions(section, pickupCityDropdown, dropCityDropdown);
            handleDropdownChange(section);
        });

        dropCityDropdown.addEventListener('change', function () {
            updateDropdownOptions(section, dropCityDropdown, pickupCityDropdown);
            handleDropdownChange(section);
        });
    }

    // Apply event listeners to each section
    const sections = document.querySelectorAll('.tab-content');
    sections.forEach(section => {
        addDropdownEventListeners(section);
    });

    // Automatically select "Airport Pickup" tab and "Budget" nested tab on page load
    document.querySelector('.tab-link[data-target="#tab-airport-pickup"]').click();
    document.querySelector('.nested-tab-link[data-target="#tab-airport-pickup-budget"]').click();

    // Remove "Bandaranaike International Airport" from the other dropdowns if selected
    function removeAirportOption(section) {
        const pickupCityDropdown = section.querySelector('#pickup-city-dropdown');
        const dropCityDropdown = section.querySelector('#drop-city-dropdown');

        const airportOptionText = "Bandaranaike International Airport";

        pickupCityDropdown.addEventListener('change', function () {
            if (pickupCityDropdown.options[pickupCityDropdown.selectedIndex].text === airportOptionText) {
                Array.from(dropCityDropdown.options).forEach(option => {
                    if (option.text === airportOptionText) {
                        option.style.display = 'none';
                    }
                });
            } else {
                Array.from(dropCityDropdown.options).forEach(option => {
                    if (option.text === airportOptionText) {
                        option.style.display = 'block';
                    }
                });
            }
        });

        dropCityDropdown.addEventListener('change', function () {
            if (dropCityDropdown.options[dropCityDropdown.selectedIndex].text === airportOptionText) {
                Array.from(pickupCityDropdown.options).forEach(option => {
                    if (option.text === airportOptionText) {
                        option.style.display = 'none';
                    }
                });
            } else {
                Array.from(pickupCityDropdown.options).forEach(option => {
                    if (option.text === airportOptionText) {
                        option.style.display = 'block';
                    }
                });
            }
        });
    }

    // Apply the airport option removal logic to each section
    sections.forEach(section => {
        removeAirportOption(section);
    });

    // function bookTrip() {
    //     const userElement = document.getElementById('txtUser');
    //     if (!userElement.value) {
    //         document.querySelector('.sign-in-btn').click();
    //         return;
    //     }

    //     const datetimeInput = document.querySelector('.tab-content.active #datetime-local');
    //     const selectedDate = new Date(datetimeInput.value);
    //     const now = new Date();

    //     if (selectedDate < now) {
    //         alert("Invalid date selection.");
    //         return;
    //     }

    //     if (selectedDate.toDateString() === now.toDateString() && selectedDate.getTime() < now.getTime()) {
    //         alert("Invalid time selection.");
    //         return;
    //     }

    //     const costElement = document.querySelector('.tab-content.active #trip-cost');
    //     const bookingData = {
    //         customerId: userElement.value,
    //         city_distance_id: costElement.value,
    //         booking_date: datetimeInput.value,
    //         fare: costElement.textContent
    //     };

    //     const bookingUrl = "http://localhost:8080/mega_city_cab-services/booking";
    //     const options = {
    //         method: "POST",
    //         headers: {
    //             "content-type": "application/json"
    //         },
    //         body: JSON.stringify(bookingData)
    //     };

    //     fetch(bookingUrl, options)
    //         .then(res => res.json())
    //         .then(data => {
    //             if (data.message === "Booking added successfully!") {
    //                 alert(data.message);
    //             } else {
    //                 alert("Booking failed.");
    //             }
    //         })
    //         .catch(error => {
    //             alert("Booking process failed.");
    //         });
    // }

    document.querySelectorAll('.book-now-btn').forEach(button => {
        button.addEventListener('click', bookTrip);
    });

});



//////////////////////////////////////////////////////////////////////////
/////////////////////////// ---- Rest API ---- ///////////////////////////
//////////////////////////////////////////////////////////////////////////

// Login
const login_url = "http://192.168.1.100:8080/mega_city_cab-services/login/";
    
function login() {
    const user = {
        "username" : document.getElementById("txtUsername_Login").value,
        "password" : document.getElementById("txtPassword_Login").value
    };

    const options = {
        method : "POST",
        headers : {
            "content-type" : "application/json"
        },
        body : JSON.stringify(user)
    };

    fetch(login_url, options)
        .then(res => res.json().then(data => ({ status: res.status, body: data })))
        .then(({ status, body }) => {
            const userRoleElement = document.getElementById('txtUser');
            
            if (status === 200) {
                alert("Welcome " + body.username + "!");
                // Hide login form and sign-in button, show user role
                document.querySelector('.login-popup').classList.remove('active');
                document.querySelector('.sign-in-btn').style.display = 'none';
                document.querySelector('.blur-wrapper').classList.remove('blur-background');
                document.body.style.overflow = '';
                
                userRoleElement.textContent = body.username;
                userRoleElement.value = body.userID;
                userRoleElement.classList.remove('hidden');
            } else {
                // alert("Invalid username or password.");
                document.getElementById("message_Login").textContent = body.message;
            }
        })
        .catch(error => {
            alert("Login Process Failed.");
        });
}

// Register - Customer
const addCustomer_url = "http://192.168.1.100:8080/mega_city_cab-services/customer/";
    
function registerCustomer() {
    const termsCheckbox = document.getElementById("termsCheckbox_CusReg");
    if (!termsCheckbox.checked) {
        alert("You must agree to the Terms & Conditions to sign up.");
        return;
    }

    const password = document.getElementById("txtPassword_CusReg").value;
    const confirmPassword = document.getElementById("txtConPassword_CusReg").value;

    if (password !== confirmPassword) {
        document.getElementById("message_CusReg").textContent = "Passwords do not match.";
        return;
    }

    const customer = {
        "username": document.getElementById("txtUsername_CusReg").value,
        "password": password,
        "email": document.getElementById("txtEmail_CusReg").value,
        "name": document.getElementById("txtFirstName_CusReg").value + " " + document.getElementById("txtLastName_CusReg").value,
        "address": document.getElementById("txtAddress_CusReg").value,
        "nic": document.getElementById("txtNIC_CusReg").value,
        "phoneNumber": document.getElementById("txtPhone_CusReg").value
    };

    const options = {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(customer)
    };

    fetch(addCustomer_url, options)
        .then(res => res.json().then(data => ({ status: res.status, body: data })))
        .then(({ status, body }) => {
            if (status === 201) {
                alert(body.message);
                document.getElementById("message_CusReg").textContent = body.message;
                // Hide sign-up form and show login form
                document.querySelector('.signup-popup').classList.remove('active');
                document.querySelector('.login-popup').classList.add('active');
            } else {
                if (status === 501){
                    alert("Your data already exists.");
                }
                else{
                    document.getElementById("message_CusReg").textContent = body.message;
                }
            }
        })
        .catch(error => {
            alert("Registration Process Failed.");
        });
}

// Create - Booking
function bookTrip() {
    const userElement = document.getElementById('txtUser');
    if (!userElement.value) {
        document.querySelector('.sign-in-btn').click();
        return;
    }

    const datetimeInput = document.querySelector('.tab-content.active #datetime-local');
    const selectedDate = new Date(datetimeInput.value);
    const now = new Date();

    if (selectedDate < now) {
        alert("Invalid date selection.");
        return;
    }

    if (selectedDate.toDateString() === now.toDateString() && selectedDate.getTime() < now.getTime()) {
        alert("Invalid time selection.");
        return;
    }

    const costElement = document.querySelector('.tab-content.active #trip-cost');
    const cityDistanceId = costElement.value;
    const fare = parseFloat(costElement.textContent.replace(/[^0-9.-]+/g, ""));

    if (cityDistanceId <= 0) {
        alert("Need to select locations again.");
        return;
    }

    if (fare <= 0) {
        alert("Invalid cost amount.");
        return;
    }

    const bookingData = {
        customerId: userElement.value,
        city_distance_id: cityDistanceId,
        booking_date: new Date(datetimeInput.value).toLocaleString('en-US', { month: 'short', day: 'numeric', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }).replace(/(\d{4}),/, '$1'),
        fare: fare
    };

    const bookingUrl = "http://192.168.1.100:8080/mega_city_cab-services/booking";
    const options = {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(bookingData)
    };

    fetch(bookingUrl, options)
        .then(res => res.json())
        .then(data => {
            if (data.message === "Booking added successfully!") {
                alert(data.message);
            } else {
                alert("Booking failed.");
            }
        })
        .catch(error => {
            alert("Booking process failed.");
        });
}

document.querySelectorAll('.book-now-btn').forEach(button => {
    button.addEventListener('click', bookTrip);
});
