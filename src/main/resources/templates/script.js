function toggleGender() {
  const genderToggle = document.getElementById('genderToggle');
  const genderInput = document.getElementById('gender');
  if (genderToggle.checked) {
    genderInput.value = 'BOY';
  } else {
    genderInput.value = 'GIRL';
  }
}

document.getElementById('childForm').addEventListener('submit', function(event) {
  event.preventDefault();
  const form = event.target;
  // Add validation logic here if needed
  console.log('Form submitted:', new FormData(form));
});