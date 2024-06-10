function addEvent() {
	let date = eventDateInput.value;
	let title = eventTitleInput.value;
	let description = eventDescriptionInput.value;
	let role = "TEACHER";

	if (date && title) {
		let eventId = eventIdCounter++;

		events.push(
			{
				id: eventId, date: date,
				title: title,
				description: description
			}
		);
		showCalendar(currentMonth, currentYear);
		eventDateInput.value = "";
		eventTitleInput.value = "";
		eventDescriptionInput.value = "";
		displayReminders();

        // Добавяне на ролята към данните, които ще бъдат изпратени
        let data = {
            name: title,
            date: date,
            description: description,
            role: role // Пример за роля, която да добавите
        };

        // Изпращане на заявката с добавения HTTP хедър
        $.ajax({
            type: "POST",
            url: "http://localhost:8070/saveEventa",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function(response) {
                console.log("Success:", response);
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
	}
}
