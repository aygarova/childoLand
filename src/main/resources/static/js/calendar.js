$(document).ready(function() {
    var selectedDate = null;

    $('#calendar').fullCalendar({
        selectable: true,
        selectHelper: true,
        select: function(start, end) {
            selectedDate = moment(start).format('YYYY-MM-DD');
            alert('Selected date: ' + selectedDate);
        }
    });

    $('#time').timepicker({
        timeFormat: 'HH:mm:ss',
        interval: 30,
        minTime: '0',
        maxTime: '23:59',
        defaultTime: '',
        startTime: '00:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });

    $('#eventForm').on('submit', function(e) {
        e.preventDefault();

        var name = $('#name').val();
        var description = $('#description').val();
        var time = $('#time').val();

        if (!selectedDate) {
            alert('Please select a date from the calendar first.');
            return;
        }

        var dateTime = selectedDate + 'T' + time;
        var currentDateTime = moment().format('YYYY-MM-DDTHH:mm:ss');

        if (moment(dateTime).isBefore(currentDateTime)) {
            alert('The selected date and time are in the past. Please choose a future date and time.');
        } else {
            saveEvent(name, description, dateTime);
        }
    });
});