
$(function() {
	// Sidebar toggle behavior
	$('#sidebarCollapse').on('click', function() {
		$('#sidebar, #content').toggleClass('active');
	});
});


/*  ==========================================
	SHOW UPLOADED IMAGE
* ========================================== */
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#imageResult')
				.attr('src', e.target.result);
		};
		reader.readAsDataURL(input.files[0]);
	}
}

$(function() {
	$('#upload').on('change', function() {
		readURL(input);
	});
});

/*  ==========================================
	SHOW UPLOADED IMAGE NAME
* ========================================== */
var input = document.getElementById('upload');
var infoArea = document.getElementById('upload-label');

input.addEventListener('change', showFileName);
function showFileName(event) {
	var input = event.srcElement;
	var fileName = input.files[0].name;
	infoArea.textContent = 'File name: ' + fileName;
}


/*FETCHING STATES DATA*/
function fetchStates() {
	var countryId = $('#country').val();

	// Clear existing options
	$('#state').empty();

	if (countryId !== '') {
		// Fetch states via AJAX
		$.get('/worker/states/' + countryId, function(states) {
			// Populate the state select element with options
			$.each(states, function(index, state) {
				$('#state').append($('<option>', {
					value: state.id,
					text: state.stateName
				}));
			});
		}).fail(function() {
			console.log('Failed to fetch states.');
		});
	}
}


/*FETCHING DISTRIC DATA*/

function fetchDistrics() {
	var stateId = $('#state').val();
	// Clear existing options
	$('#distric').empty();
	if (stateId !== '') {
		// fetch distric via ajax
		$.get('/worker/distric/' + stateId, function(districs) {
			//populate the state select element with options
			$.each(districs, function(index, distric) {
				$('#distric').append($('<option>', {
					value: distric.id,
					text: distric.districName
				}));
			});
		}).fail(function() {
			console.log('Failed to fetch states.');
		});
	}
}


/* Enlarge Image*/