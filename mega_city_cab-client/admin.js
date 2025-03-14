
document.getElementById('adminButton').addEventListener('click', function() {
    var dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.classList.toggle('hidden');
});

function showSection(sectionId) {
    var sections = ["welcomeSection","customerSection", "driverSection", "vehicleSection", "bookingSection"];
    sections.forEach(function(id) {
        document.getElementById(id).classList.add('hidden');
    });
    document.getElementById(sectionId).classList.remove('hidden');

    // Fetch data for the customer section
    if (sectionId === "customerSection") {
        fetch('http://192.168.1.100:8080/mega_city_cab-services/customer/')
            .then(response => response.json())
            .then(data => {
                var customerTableBody = document.getElementById('customerTableBody');
                customerTableBody.innerHTML = '';
                data.forEach((customer, index) => { // Added index parameter
                    var row = `<tr class="hover:bg-gray-600" onclick="selectRow(this)">
                        <td class="px-4 py-2">${index + 1}</td> <!-- Added custom count down -->
                        <td class="px-4 py-2">${customer.customerId}</td>
                        <td class="px-4 py-2">${customer.name}</td>
                        <td class="px-4 py-2">${customer.address}</td>
                        <td class="px-4 py-2">${customer.nic}</td>
                        <td class="px-4 py-2">${customer.phoneNumber}</td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-green-500 hover:bg-green-400" onclick="updateCustomer(this)">Update</button></td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-red-500 hover:bg-red-400" onclick="deleteCustomer(this)">Delete</button></td>
                    </tr>`;
                    customerTableBody.insertAdjacentHTML('beforeend', row);
                });

                // Disable buttons by default
                document.querySelectorAll('#customerTableBody button').forEach(button => {
                    button.classList.add('disabled-button');
                    button.disabled = true;
                });
            })
            .catch(error => console.error('Error fetching customer data:', error));
    } 
    // Fetch data for the driver section
    else if (sectionId === "driverSection") {
        fetch('http://192.168.1.100:8080/mega_city_cab-services/driver/')
            .then(response => response.json())
            .then(data => {
                var driverTableBody = document.getElementById('driverTableBody');
                driverTableBody.innerHTML = '';
                data.forEach((driver, index) => {
                    var row = `<tr class="hover:bg-gray-600" onclick="selectDriverRow(this)">
                        <td class="px-4 py-2">${index + 1}</td>
                        <td class="px-4 py-2">${driver.driverId}</td>
                        <td class="px-4 py-2">${driver.name}</td>
                        <td class="px-4 py-2">${driver.address}</td>
                        <td class="px-4 py-2">${driver.nic}</td>
                        <td class="px-4 py-2">${driver.phoneNumber}</td>
                        <td class="px-4 py-2">${driver.licensenNumber}</td>
                        <td class="px-4 py-2">${driver.availability ? 'Available' : 'Unavailable'}</td>
                        <td class="px-4 py-2">${driver.rating.toFixed(1)}</td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-green-500 hover:bg-green-400" onclick="updateDriver(this)">Update</button></td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-red-500 hover:bg-red-400" onclick="deleteDriver(this)">Delete</button></td>
                    </tr>`;
                    driverTableBody.insertAdjacentHTML('beforeend', row);
                });

                // Disable buttons by default
                document.querySelectorAll('#driverTableBody button').forEach(button => {
                    button.classList.add('disabled-button');
                    button.disabled = true;
                });
            })
            .catch(error => console.error('Error fetching driver data:', error));
    }
    // Fetch data for the vehicle section
    else if (sectionId === "vehicleSection") {
        fetch('http://192.168.1.100:8080/mega_city_cab-services/vehicle/')
            .then(response => response.json())
            .then(data => {
                var vehicleTableBody = document.getElementById('vehicleTableBody');
                vehicleTableBody.innerHTML = '';
                data.forEach((vehicle, index) => {
                    var row = `<tr class="hover:bg-gray-600">
                        <td class="px-4 py-2">${index + 1}</td>
                        <td class="px-4 py-2">${vehicle.vehicleId}</td>
                        <td class="px-4 py-2">${vehicle.vehicleType}</td>
                        <td class="px-4 py-2">${vehicle.model}</td>
                        <td class="px-4 py-2">${vehicle.capacity}</td>
                        <td class="px-4 py-2">${vehicle.condition}</td>
                        <td class="px-4 py-2">${vehicle.baggagers}</td>
                        <td class="px-4 py-2">${vehicle.licensePlate}</td>
                        <td class="px-4 py-2">${vehicle.price}</td>
                        <td class="px-4 py-2">${vehicle.availability ? 'Available' : 'Unavailable'}</td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-red-500 hover:bg-red-400" onclick="deleteVehicle(this)">Delete</button></td>
                    </tr>`;
                    vehicleTableBody.insertAdjacentHTML('beforeend', row);
                });
            })
            .catch(error => console.error('Error fetching vehicle data:', error));
    }
    // Fetch data for the booking section
    else if (sectionId === "bookingSection") {
        fetch('http://192.168.1.100:8080/mega_city_cab-services/booking/')
            .then(response => response.json())
            .then(data => {
                var bookingTableBody = document.getElementById('bookingTableBody');
                bookingTableBody.innerHTML = '';
                data.forEach((booking, index) => {
                    var row = `<tr class="hover:bg-gray-600" onclick="selectBookingRow(this)">
                        <td class="px-4 py-2">${index + 1}</td>
                        <td class="px-4 py-2">${booking.bookingId}</td>
                        <td class="px-4 py-2">${booking.customerId}</td>
                        <td class="px-4 py-2">${booking.vehicleId}</td>
                        <td class="px-4 py-2">${booking.driverId}</td>
                        <td class="px-4 py-2">${booking.city_distance_id}</td>
                        <td class="px-4 py-2">${booking.booking_date}</td>
                        <td class="px-4 py-2">${booking.status}</td>
                        <td class="px-4 py-2">${booking.fare}</td>
                        <td class="px-4 py-2">${booking.payment_status}</td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-green-500 hover:bg-green-400" onclick="updateBooking(this)">Update</button></td>
                        <td class="px-4 py-2"><button class="px-2 py-1 text-white bg-red-500 hover:bg-red-400" onclick="deleteBooking(this)">Delete</button></td>
                    </tr>`;
                    bookingTableBody.insertAdjacentHTML('beforeend', row);
                });

                // Disable buttons by default
                document.querySelectorAll('#bookingTableBody button').forEach(button => {
                    button.classList.add('disabled-button');
                    button.disabled = true;
                });
            })
            .catch(error => console.error('Error fetching booking data:', error));
    }
}


// //////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////
// ////////////////// ----  Customer  ---- //////////////////
// //////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////

function selectRow(row) {
    // Deselect any previously selected row
    var previouslySelected = document.querySelector('.selected-row');
    if (previouslySelected && previouslySelected !== row) {
        previouslySelected.classList.remove('selected-row');
        previouslySelected.querySelectorAll('td').forEach(td => {
            td.contentEditable = false;
            td.classList.remove('editable');
        });

        // Disable buttons for the previously selected row
        previouslySelected.querySelectorAll('button').forEach(button => {
            button.classList.add('disabled-button');
            button.disabled = true;
        });

        // Reset the previously selected row values
        var prevCustomerId = previouslySelected.cells[1].innerText;
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/customer/${prevCustomerId}`)
            .then(response => response.json())
            .then(data => {
                previouslySelected.cells[2].innerText = data.name;
                previouslySelected.cells[3].innerText = data.address;
                previouslySelected.cells[4].innerText = data.nic;
                previouslySelected.cells[5].innerText = data.phoneNumber;
            })
            .catch(error => console.error('Error resetting customer data:', error));
    }

    // Select the clicked row
    row.classList.add('selected-row');
    row.querySelectorAll('td').forEach(td => {
        if (!td.querySelector('button')) { // Exclude cells with buttons
            if (td.cellIndex !== 0 && td.cellIndex !== 1 && td.cellIndex !== 4) { // Exclude "Number", "Customer ID" and "NIC" fields from being editable
                td.contentEditable = true;
                td.classList.add('editable');
            }
        }
    });

    // Enable buttons for the selected row
    row.querySelectorAll('button').forEach(button => {
        button.classList.remove('disabled-button');
        button.disabled = false;
    });

    // Add click event to reset cell data for ID cell (cellIndex=0)
    row.cells[0].addEventListener('click', function() {
        var customerId = row.cells[1].innerText;
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/customer/${customerId}`)
            .then(response => response.json())
            .then(data => {
                row.cells[2].innerText = data.name;
                row.cells[3].innerText = data.address;
                row.cells[4].innerText = data.nic;
                row.cells[5].innerText = data.phoneNumber;
            })
            .catch(error => console.error('Error resetting customer data:', error));
    });
}

// Update customer data
function updateCustomer(button) {
    var row = button.closest('tr');
    var customerId = row.cells[1].innerText;
    var name = row.cells[2].innerText;
    var address = row.cells[3].innerText;
    var phoneNumber = row.cells[5].innerText;

    var customerData = {
        customerId: customerId,
        name: name,
        address: address,
        phoneNumber: phoneNumber
    };

    fetch('http://192.168.1.100:8080/mega_city_cab-services/customer', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(customerData)
    })
    .then(response => response.json().then(data => ({ status: response.status, body: data })))
    .then(result => {
        if (result.status === 200) {
            alert(result.body.message);
            // Deselect the row after successful update
            row.classList.remove('selected-row');
            row.querySelectorAll('td').forEach(td => {
                td.contentEditable = false;
                td.classList.remove('editable');
            });
            row.querySelectorAll('button').forEach(button => {
                button.classList.add('disabled-button');
                button.disabled = true;
            });
        } else if (result.status === 400) {
            alert('Error: ' + result.body.message);
        } else if (result.status === 501) {
            alert('Error: ' + result.body.message);
        }
    })
    .catch(error => console.error('Error updating customer data:', error));
}

// Delete customer data
function deleteCustomer(button) {
    var row = button.closest('tr');
    var customerId = row.cells[1].innerText;

    if (confirm('Are you sure you want to delete this customer?')) {
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/customer/${customerId}`, {
            method: 'DELETE',
            mode: 'cors'
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(result => {
            if (result.status === 200) {
                alert(result.body.message);
                // Reload the customer section to refresh the table data
                showSection('customerSection');
            } else if (result.status === 401) {
                alert('Error: ' + result.body.message);
            }
        })
        .catch(error => console.error('Error deleting customer data:', error));
    } else {
        // Reset the row data and deselect the row if deletion is canceled
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/customer/${customerId}`)
            .then(response => response.json())
            .then(data => {
                row.cells[2].innerText = data.name;
                row.cells[3].innerText = data.address;
                row.cells[4].innerText = data.nic;
                row.cells[5].innerText = data.phoneNumber;
                row.classList.remove('selected-row');
                row.querySelectorAll('td').forEach(td => {
                    td.contentEditable = false;
                    td.classList.remove('editable');
                });
                row.querySelectorAll('button').forEach(button => {
                    button.classList.add('disabled-button');
                    button.disabled = true;
                });
            })
            .catch(error => console.error('Error resetting customer data:', error));
    }
}


// //////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////
// /////////////////// ----  Driver  ---- ///////////////////
// //////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////

// Select driver row
function selectDriverRow(row) {
    // Deselect any previously selected row
    var previouslySelected = document.querySelector('.selected-row');
    if (previouslySelected && previouslySelected !== row) {
        previouslySelected.classList.remove('selected-row');
        previouslySelected.querySelectorAll('td').forEach(td => {
            td.contentEditable = false;
            td.classList.remove('editable');
        });

        // Disable buttons for the previously selected row
        previouslySelected.querySelectorAll('button').forEach(button => {
            button.classList.add('disabled-button');
            button.disabled = true;
        });

        // Reset the previously selected row values
        var prevDriverId = previouslySelected.cells[1].innerText;
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/driver/${prevDriverId}`)
            .then(response => response.json())
            .then(data => {
                previouslySelected.cells[2].innerText = data.name;
                previouslySelected.cells[3].innerText = data.address;
                previouslySelected.cells[4].innerText = data.nic;
                previouslySelected.cells[5].innerText = data.phoneNumber;
                previouslySelected.cells[6].innerText = data.licensenNumber;
                previouslySelected.cells[7].innerText = data.availability ? 'Available' : 'Unavailable';
                previouslySelected.cells[8].innerText = data.rating.toFixed(1);
            })
            .catch(error => console.error('Error resetting driver data:', error));
    }

    // Select the clicked row
    row.classList.add('selected-row');
    row.querySelectorAll('td').forEach(td => {
        if (!td.querySelector('button')) { // Exclude cells with buttons
            if (td.cellIndex !== 0 && td.cellIndex !== 1 && td.cellIndex !== 4 && td.cellIndex !== 8 && td.cellIndex !== 7) { // Exclude "Number", "Driver ID", "NIC", "Rating" and "Availability" fields from being editable
                td.contentEditable = true;
                td.classList.add('editable');
            }
        }
    });

    // Enable buttons for the selected row
    row.querySelectorAll('button').forEach(button => {
        button.classList.remove('disabled-button');
        button.disabled = false;
    });

    // Add click event to reset cell data for ID cell (cellIndex=0)
    row.cells[0].addEventListener('click', function() {
        var driverId = row.cells[1].innerText;
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/driver/${driverId}`)
            .then(response => response.json())
            .then(data => {
                row.cells[2].innerText = data.name;
                row.cells[3].innerText = data.address;
                row.cells[4].innerText = data.nic;
                row.cells[5].innerText = data.phoneNumber;
                row.cells[6].innerText = data.licensenNumber;
                row.cells[7].innerText = data.availability ? 'Available' : 'Unavailable';
                row.cells[8].innerText = data.rating.toFixed(1);
            })
            .catch(error => console.error('Error resetting driver data:', error));
    });

    // Add click event to toggle availability for Availability cell (cellIndex=7)
    row.cells[7].classList.add('availability-cell');
    row.cells[7].addEventListener('click', function() {
        if (row.classList.contains('selected-row')) {
            var availabilityCell = row.cells[7];
            availabilityCell.innerText = availabilityCell.innerText === 'Available' ? 'Unavailable' : 'Available';
        }
    });
}

// Update driver data
function updateDriver(button) {
    var row = button.closest('tr');
    var driverId = row.cells[1].innerText;
    var name = row.cells[2].innerText;
    var address = row.cells[3].innerText;
    var phoneNumber = row.cells[5].innerText;
    var licensenNumber = row.cells[6].innerText;
    var availability = row.cells[7].innerText === 'Available';

    var driverData = {
        driverId: driverId,
        name: name,
        address: address,
        phoneNumber: phoneNumber,
        licensenNumber: licensenNumber,
        availability: availability
    };

    fetch('http://192.168.1.100:8080/mega_city_cab-services/driver', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(driverData)
    })
    .then(response => response.json().then(data => ({ status: response.status, body: data })))
    .then(result => {
        if (result.status === 200) {
            alert(result.body.message);
            // Deselect the row after successful update
            row.classList.remove('selected-row');
            row.querySelectorAll('td').forEach(td => {
                td.contentEditable = false;
                td.classList.remove('editable');
            });
            row.querySelectorAll('button').forEach(button => {
                button.classList.add('disabled-button');
                button.disabled = true;
            });
        } else if (result.status === 400) {
            alert('Error: ' + result.body.message);
        } else if (result.status === 501) {
            alert('Error: ' + result.body.message);
        }
    })
    .catch(error => console.error('Error updating driver data:', error));
}

// Delete driver data
function deleteDriver(button) {
    var row = button.closest('tr');
    var driverId = row.cells[1].innerText;

    if (confirm('Are you sure you want to delete this driver?')) {
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/driver/${driverId}`, {
            method: 'DELETE',
            mode: 'cors'
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(result => {
            if (result.status === 200) {
                alert(result.body.message);
                // Reload the driver section to refresh the table data
                showSection('driverSection');
            } else if (result.status === 401) {
                alert('Error: ' + result.body.message);
            }
        })
        .catch(error => console.error('Error deleting driver data:', error));
    } else {
        // Reset the row data and deselect the row if deletion is canceled
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/driver/${driverId}`)
            .then(response => response.json())
            .then(data => {
                row.cells[2].innerText = data.name;
                row.cells[3].innerText = data.address;
                row.cells[4].innerText = data.nic;
                row.cells[5].innerText = data.phoneNumber;
                row.cells[6].innerText = data.licensenNumber;
                row.cells[7].innerText = data.availability ? 'Available' : 'Unavailable';
                row.cells[8].innerText = data.rating.toFixed(1);
                row.classList.remove('selected-row');
                row.querySelectorAll('td').forEach(td => {
                    td.contentEditable = false;
                    td.classList.remove('editable');
                });
                row.querySelectorAll('button').forEach(button => {
                    button.classList.add('disabled-button');
                    button.disabled = true;
                });
            })
            .catch(error => console.error('Error resetting driver data:', error));
    }
}

// Delete vehicle data
function deleteVehicle(button) {
    var row = button.closest('tr');
    var vehicleId = row.cells[1].innerText;

    if (confirm('Are you sure you want to delete this vehicle?')) {
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/vehicle/${vehicleId}`, {
            method: 'DELETE',
            mode: 'cors'
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(result => {
            if (result.status === 200) {
                alert(result.body.message);
                // Reload the vehicle section to refresh the table data
                showSection('vehicleSection');
            } else if (result.status === 401) {
                alert('Error: ' + result.body.message);
            }
        })
        .catch(error => console.error('Error deleting vehicle data:', error));
    }
}


// //////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////
// ////////////////// ----  Booking  ---- ///////////////////
// //////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////

// Select booking row
function selectBookingRow(row) {
    // Deselect any previously selected row
    var previouslySelected = document.querySelector('.selected-row');
    if (previouslySelected && previouslySelected !== row) {
        previouslySelected.classList.remove('selected-row');
        previouslySelected.querySelectorAll('td').forEach(td => {
            td.contentEditable = false;
            td.classList.remove('editable');
        });

        // Disable buttons for the previously selected row
        previouslySelected.querySelectorAll('button').forEach(button => {
            button.classList.add('disabled-button');
            button.disabled = true;
        });

        // Reset the previously selected row values
        var prevBookingId = previouslySelected.cells[1].innerText;
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/booking/${prevBookingId}`)
            .then(response => response.json())
            .then(data => {
                previouslySelected.cells[2].innerText = data.customerId;
                previouslySelected.cells[3].innerText = data.vehicleId;
                previouslySelected.cells[4].innerText = data.driverId;
                previouslySelected.cells[5].innerText = data.city_distance_id;
                previouslySelected.cells[6].innerText = data.booking_date;
                previouslySelected.cells[7].innerText = data.status;
                previouslySelected.cells[8].innerText = data.fare;
                previouslySelected.cells[9].innerText = data.payment_status;
            })
            .catch(error => console.error('Error resetting booking data:', error));
    }

    // Select the clicked row
    row.classList.add('selected-row');
    row.querySelectorAll('td').forEach(td => {
        if (!td.querySelector('button')) { // Exclude cells with buttons
            // Exclude "indexNumber", "Booking ID", "Customer ID", "City Distance ID", "Booking Date" and "Fare" fields from being editable
            if (td.cellIndex !== 0 && td.cellIndex !== 1 && td.cellIndex !== 2 && td.cellIndex !== 5 && td.cellIndex !== 6 && td.cellIndex !== 8) { 
                td.contentEditable = true;
                td.classList.add('editable');
            }
        }
    });

    // Enable buttons for the selected row
    row.querySelectorAll('button').forEach(button => {
        button.classList.remove('disabled-button');
        button.disabled = false;
    });

    // Add click event to reset cell data for ID cell (cellIndex=0)
    row.cells[0].addEventListener('click', function() {
        var bookingId = row.cells[1].innerText;
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/booking/${bookingId}`)
            .then(response => response.json())
            .then(data => {
                row.cells[2].innerText = data.customerId;
                row.cells[3].innerText = data.vehicleId;
                row.cells[4].innerText = data.driverId;
                row.cells[5].innerText = data.city_distance_id;
                row.cells[6].innerText = data.booking_date;
                row.cells[7].innerText = data.status;
                row.cells[8].innerText = data.fare;
                row.cells[9].innerText = data.payment_status;
            })
            .catch(error => console.error('Error resetting booking data:', error));
    });

    // Add click event to show dropdown for Vehicle ID cell (cellIndex=3)
    row.cells[3].addEventListener('click', function(event) {
        if (row.classList.contains('selected-row') && !row.cells[3].querySelector('select')) {
            fetch('http://192.168.1.100:8080/mega_city_cab-services/vehicle/available')
                .then(response => response.json())
                .then(data => {
                    var dropdown = document.createElement('select');
                    dropdown.classList.add('editable');
                    data.forEach(vehicleId => {
                        var option = document.createElement('option');
                        option.value = vehicleId;
                        option.text = vehicleId;
                        dropdown.appendChild(option);
                    });
                    dropdown.value = row.cells[3].innerText;
                    row.cells[3].innerHTML = '';
                    row.cells[3].appendChild(dropdown);

                    dropdown.addEventListener('change', function() {
                        row.cells[3].innerText = dropdown.value;
                    });

                    // Prevent the dropdown from disappearing when selecting an option
                    event.stopPropagation();
                })
                .catch(error => console.error('Error fetching available vehicles:', error));
        }
    });

    // Add click event to show dropdown for Driver ID cell (cellIndex=4)
    row.cells[4].addEventListener('click', function(event) {
        if (row.classList.contains('selected-row') && !row.cells[4].querySelector('select')) {
            fetch('http://192.168.1.100:8080/mega_city_cab-services/driver/available')
                .then(response => response.json())
                .then(data => {
                    var dropdown = document.createElement('select');
                    dropdown.classList.add('editable');
                    data.forEach(driverId => {
                        var option = document.createElement('option');
                        option.value = driverId;
                        option.text = driverId;
                        dropdown.appendChild(option);
                    });
                    dropdown.value = row.cells[4].innerText;
                    row.cells[4].innerHTML = '';
                    row.cells[4].appendChild(dropdown);

                    dropdown.addEventListener('change', function() {
                        row.cells[4].innerText = dropdown.value;
                    });

                    // Prevent the dropdown from disappearing when selecting an option
                    event.stopPropagation();
                })
                .catch(error => console.error('Error fetching available drivers:', error));
        }
    });

    // Add click event to show dropdown for Status cell (cellIndex=7)
    row.cells[7].addEventListener('click', function(event) {
        if (row.classList.contains('selected-row') && !row.cells[7].querySelector('select')) {
            var dropdown = document.createElement('select');
            dropdown.classList.add('editable');
            ['Pending', 'Completed', 'Cancelled'].forEach(status => {
                var option = document.createElement('option');
                option.value = status;
                option.text = status;
                dropdown.appendChild(option);
            });
            dropdown.value = row.cells[7].innerText;
            row.cells[7].innerHTML = '';
            row.cells[7].appendChild(dropdown);

            dropdown.addEventListener('change', function() {
                row.cells[7].innerText = dropdown.value;
            });

            // Prevent the dropdown from disappearing when selecting an option
            event.stopPropagation();
        }
    });

    // Add click event to toggle payment status for Payment Status cell (cellIndex=9)
    row.cells[9].classList.add('availability-cell');
    row.cells[9].addEventListener('click', function() {
        if (row.classList.contains('selected-row')) {
            var paymentStatusCell = row.cells[9];
            paymentStatusCell.innerText = paymentStatusCell.innerText === 'Paid' ? 'Unpaid' : 'Paid';
        }
    });

    // Remove text cursor from Vehicle ID, Driver ID, Status, and Payment Status cells
    row.cells[3].contentEditable = false;
    row.cells[3].classList.remove('editable');
    row.cells[4].contentEditable = false;
    row.cells[4].classList.remove('editable');
    row.cells[7].contentEditable = false;
    row.cells[7].classList.remove('editable');
    row.cells[9].contentEditable = false;
    row.cells[9].classList.remove('editable');
}

// Update booking data
function updateBooking(button) {
    var row = button.closest('tr');
    var bookingId = row.cells[1].innerText;
    var vehicleId = row.cells[3].innerText;
    var driverId = row.cells[4].innerText;
    var status = row.cells[7].innerText;
    var payment_status = row.cells[9].innerText;

    var bookingData = {
        bookingId: bookingId,
        vehicleId: vehicleId,
        driverId: driverId,
        status: status,
        payment_status: payment_status
    };

    fetch('http://192.168.1.100:8080/mega_city_cab-services/booking', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bookingData)
    })
    .then(response => response.json().then(data => ({ status: response.status, body: data })))
    .then(result => {
        if (result.status === 200) {
            alert(result.body.message);
            // Deselect the row after successful update
            row.classList.remove('selected-row');
            row.querySelectorAll('td').forEach(td => {
                td.contentEditable = false;
                td.classList.remove('editable');
            });
            row.querySelectorAll('button').forEach(button => {
                button.classList.add('disabled-button');
                button.disabled = true;
            });
        } else if (result.status === 400) {
            alert('Error: ' + result.body.message);
        } else if (result.status === 501) {
            alert('Error: ' + result.body.message);
        }
    })
    .catch(error => console.error('Error updating booking data:', error));
}

// Delete booking data
function deleteBooking(button) {
    var row = button.closest('tr');
    var bookingId = row.cells[1].innerText;

    if (confirm('Are you sure you want to delete this booking?')) {
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/booking/${bookingId}`, {
            method: 'DELETE',
            mode: 'cors'
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(result => {
            if (result.status === 200) {
                alert(result.body.message);
                // Reload the booking section to refresh the table data
                showSection('bookingSection');
            } else if (result.status === 401) {
                alert('Error: ' + result.body.message);
            }
        })
        .catch(error => console.error('Error deleting booking data:', error));
    } else {
        // Reset the row data and deselect the row if deletion is canceled
        fetch(`http://192.168.1.100:8080/mega_city_cab-services/booking/${bookingId}`)
            .then(response => response.json())
            .then(data => {
                row.cells[2].innerText = data.customerId;
                row.cells[3].innerText = data.vehicleId;
                row.cells[4].innerText = data.driverId;
                row.cells[5].innerText = data.city_distance_id;
                row.cells[6].innerText = data.booking_date;
                row.cells[7].innerText = data.status;
                row.cells[8].innerText = data.fare;
                row.cells[9].innerText = data.payment_status;
                row.classList.remove('selected-row');
                row.querySelectorAll('td').forEach(td => {
                    td.contentEditable = false;
                    td.classList.remove('editable');
                });
                row.querySelectorAll('button').forEach(button => {
                    button.classList.add('disabled-button');
                    button.disabled = true;
                });
            })
            .catch(error => console.error('Error resetting booking data:', error));
    }
}