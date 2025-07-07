// Initialisation de Bootstrap
const bootstrap = require('bootstrap');

// Gestionnaire de clic pour les boutons de réservation
const reservationButtons = document.querySelectorAll('[data-bs-toggle="modal"]');
reservationButtons.forEach(button => {
    button.addEventListener('click', function() {
        const livreId = this.getAttribute('data-livre-id');
        document.getElementById('livreId').value = livreId;
    });
});
