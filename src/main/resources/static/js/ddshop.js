var tableId;

var vestFlag;
var somiFlag;
var quanFlag;
var giayFlag;
var phukienFlag;

$(document).ready(function($) {
	tableId = 'vestTable';
	var firstUrl = '/danhmuc/vest';
	loadTable(firstUrl);

	vestFlag = true;
	somiFlag = false;
	quanFlag = false;
	giayFlag = false;
	phukienFlag = false;

	// handle delete form submit
	$("#delete-form").submit(function(event) {
		event.preventDefault();
		submitDeleteForm();
	});

	// handle update form submit
	$("#update-form").submit(function(event) {
		event.preventDefault();
		submitUpdateForm();
	})

	$("#add-form").submit(function(event) {
		event.preventDefault();
		submitAddForm();
		// clearAddForm();
	});
	
	$("#upload-form").submit(function(event) {
		event.preventDefault();
		submitUploadForm();
		// clearAddForm();
	});

	$(".nav-tabs a").click(function() {
		$(this).tab('show');
		var id = $(this).attr("id");
		switch (id) {
		case 'vestTab':
			tableId = 'vestTable';
			if (vestFlag === false) {
				loadTable('/danhmuc/vest');
				vestFlag = true;
			}
			break;
		case 'somiTab':
			tableId = 'somiTable';
			if (somiFlag === false) {
				loadTable('/danhmuc/somi');
				somiFlag = true;
			}
			break;
		case 'quanTab':
			tableId = 'quanTable'
			if (quanFlag === false) {
				loadTable('/danhmuc/quantay');
				quanFlag = true;
			}
			break;
		case 'giayTab':
			tableId = 'giayTable'
			if (giayFlag === false) {
				loadTable('/danhmuc/giay');
				giayFlag = true;
			}
			break;
		case 'phukienTab':
			tableId = 'otherTable'
			if (phukienFlag === false) {
				loadTable('/danhmuc/phukien');
				phukienFlag = true;
			}
			break;
		default:
			break;
		}
	});
});

function loadTable(url) {
	var index = 1;
	var table = $('#' + tableId)
			.DataTable(
					{
						"lengthMenu" : [ [ 5, 10, 20, -1 ],
								[ 5, 10, 20, "All" ] ], // change
						"ajax" : {
							"url" : url,
							"dataSrc" : ""
						},
						"columns" : [
								{
									"data" : "productID",
									"visible" : false
								// hide the column processID
								},
								{
									"data" : "productCode"
								},
								{
									"data" : "productName",
								},
								{
									"data" : "price",
									"render" : formatPrice
								},
								{
									"data" : "listSize",
								},
								{
									"data" : "percentSaleOff",
									"render" : addpercent
								},
								{
									"data" : "CreateDate",
									"render" : formatDate
								},
								{
									"data" : "description",
									"visible" : false
								},
								{
									"data" : "categoryID",
									"visible" : false
								},
								{
									"data" : null,
									"defaultContent" : "<button style = 'margin-right: 10px;' class='btn btn-info' onclick='openView(this)'>Xem</button>"
											+ "<button style = 'margin-right: 10px;' class='btn btn-warning' onclick='openUploadModal(this)'>Upload</button>"
											+ "<button style = 'margin-right: 10px;' class='btn btn-success' onclick='openUpdateModal(this)'>Sửa</button>"
											+ "<button class='btn btn-danger' onclick='openDeleteModal(this)'>Xóa</button>",

								} ],
						"order" : [ [ 7, "desc" ] ]
					});
}

// ajax jquery dataTables reload
function reloadTable() {
	setTimeout(function() {
		$('#' + tableId).DataTable().ajax.reload(null, false);// reload
																// without
		// come back to the
		// first page
	}, 200); // reload the table after 0.2s
}

var formatDate = function(data) {
	var d = new Date(data);
	return [ d.getDate().padLeft(), (d.getMonth() + 1).padLeft(),
			d.getFullYear() ].join('/')
			+ ' '
			+ [ d.getHours().padLeft(), d.getMinutes().padLeft(),
					d.getSeconds().padLeft() ].join(':');
}

Number.prototype.padLeft = function(base, chr) {
	var len = (String(base || 10).length - String(this).length) + 1;
	return len > 0 ? new Array(len).join(chr || '0') + this : this;
}

var addpercent = function(data) {
	return data + ' %';
}

var formatPrice = function(data) {
	var frm = parseFloat(data.toString().replace(/,/g, "")).toString().replace(
			/\B(?=(\d{3})+(?!\d))/g, ",");
	return frm + ' VNĐ';
}

function previewFile() {
	var preview = document.querySelector('img'); // selects the query named
													// img
	var file = document.querySelector('input[type=file]').files[0]; // sames as
																	// here
	var reader = new FileReader();

	reader.onloadend = function() {
		preview.src = reader.result;
	}

	if (file) {
		reader.readAsDataURL(file); // reads the data as a URL
	} else {
		preview.src = '';
	}
}

// open delete confirm modal
function openDeleteModal(element) {
	var data = $("#" + tableId).DataTable().row($(element).parents('tr'))
			.data();
	$("#delete-form-productID").val(data.productID);
	$("#delete-modal").modal('toggle');
}

// handle delete form submit
function submitDeleteForm() {
	var productID = $("#delete-form-productID").val()
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/sanpham/changeActive/" + productID
	});
	$("#delete-modal").modal("hide");
	reloadTable();
}

// perform ajax call to save report
function submitAddForm() {
	var categoryID = $('#add-form-categoryID :selected').val();
	var arr = $("#add-form-price").val().split(",");
	var price = arr.join('');
	var photoName = $("#add-form-code").val();
	var file = $('#add-form-photo').get(0).files[0];

	// var photo = $("#add-form-photo").val();
	var product = {
		"productCode" : $("#add-form-code").val(),
		"productName" : $("#add-form-name").val(),
		"price" : price,
		"listSize" : $("#add-form-size").val(),
		"description" : $("#add-form-description").val(),
		"categoryID" : categoryID,
		"photo" : photoName
	};

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/sanpham/themsanpham",
		data : JSON.stringify(product)
	});

	$("#add-modal").modal("hide");

	switch (categoryID) {
	case '1':
		tableId = 'vestTable';
		if (vestFlag === true) {
			reloadTable();
		}
		break;
	case '2':
		tableId = 'somiTable';
		if (somiFlag === true) {
			reloadTable();
		}
		break;
	case '3':
		tableId = 'quanTable'
		if (quanFlag === true) {
			reloadTable();
		}
		break;
	case '4':
		tableId = 'giayTable'
		if (giayFlag === true) {
			reloadTable();
		}
		break;
	case '5':
		tableId = 'otherTable'
		if (phukienFlag === true) {
			reloadTable();
		}
		break;
	default:
		break;
	}

	// clearStatus();
}

$('#add-form-price').on("keyup", function(event) {
	var selection = window.getSelection().toString();
	if (selection !== '') {
		return;
	}
	// 2.
	if ($.inArray(event.keyCode, [ 38, 40, 37, 39 ]) !== -1) {
		return;
	}
	var $this = $(this);
	// Get the value.
	var input = $this.val();

	var input = input.replace(/[\D\s\._\-]+/g, "");
	input = input ? parseInt(input, 10) : 0;
	$this.val(function() {
		return (input === 0) ? "" : input.toLocaleString("en-US");
	});

});

$('#update-form-price').on("keyup", function(event) {
	var selection = window.getSelection().toString();
	if (selection !== '') {
		return;
	}
	// 2.
	if ($.inArray(event.keyCode, [ 38, 40, 37, 39 ]) !== -1) {
		return;
	}
	var $this = $(this);
	// Get the value.
	var input = $this.val();

	var input = input.replace(/[\D\s\._\-]+/g, "");
	input = input ? parseInt(input, 10) : 0;
	$this.val(function() {
		return (input === 0) ? "" : input.toLocaleString("en-US");
	});

});

var curr;
function openUpdateModal(element) {
	var data = $("#" + tableId).DataTable().row($(element).parents('tr'))
			.data();
	$("#update-form-id").val(data.productID);
	$("#update-form-code").val(data.productCode);
	$("#update-form-name").val(data.productName);
	$("#update-form-price").val(data.price);
	$("#update-form-categoryID").val(data.categoryID);
	$("#update-form-size").val(data.listSize);
	$("#update-form-description").val(data.description);

	curr = {
		"productID" : data.productID,
		"productCode" : data.productCode,
		"productName" : data.productName,
		"price" : data.price,
		"categoryID" : data.categoryID,
		"listSize" : data.listSize,
		"description" : data.description,
	};

	$("#update-modal").modal('toggle');
}

function openView(element) {
	var data = $("#" + tableId).DataTable().row($(element).parents('tr')).data();
	var id = data.productID;
	window.location.href = "/sanpham/" + id;
}

function submitUpdateForm() {
	var categoryID = $("#update-form-categoryID").val();
	var product = {
		"productID" : $("#update-form-id").val(),
		"productCode" : $("#update-form-code").val(),
		"productName" : $("#update-form-name").val(),
		"price" : $("#update-form-price").val(),
		"categoryID" : $("#update-form-categoryID").val(),
		"listSize" : $("#update-form-size").val(),
		"description" : $("#update-form-description").val(),
	};

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/sanpham/suasanpham/" + parseInt(curr.productID),
		data : JSON.stringify(product),
	});
	$("#update-modal").modal("hide");
	switch (categoryID) {
	case '1':
		tableId = 'vestTable';
		if (vestFlag === true) {
			reloadTable();
		}
		break;
	case '2':
		tableId = 'somiTable';
		if (somiFlag === true) {
			reloadTable();
		}
		break;
	case '3':
		tableId = 'quanTable'
		if (quanFlag === true) {
			reloadTable();
		}
		break;
	case '4':
		tableId = 'giayTable'
		if (giayFlag === true) {
			reloadTable();
		}
		break;
	case '5':
		tableId = 'otherTable'
		if (phukienFlag === true) {
			reloadTable();
		}
		break;
	default:
		break;
	}
}

//open delete confirm modal
function openUploadModal(element) {
	var data = $("#" + tableId).DataTable().row($(element).parents('tr'))
			.data();
	$("#upload-form-productID").val(data.productID);
	$("#upload-modal").modal('toggle');
}

//perform ajax call to save report
function submitUploadForm() {
	var form = $('#upload-form')[0];
	var id = $("#upload-form-productID").val();
	
	 var data = new FormData(form);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/sanpham/upload/" + parseInt(id),
		data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });

	$("#upload-modal").modal("hide");
	switch (categoryID) {
	case '1':
		tableId = 'vestTable';
		if (vestFlag === true) {
			reloadTable();
		}
		break;
	case '2':
		tableId = 'somiTable';
		if (somiFlag === true) {
			reloadTable();
		}
		break;
	case '3':
		tableId = 'quanTable'
		if (quanFlag === true) {
			reloadTable();
		}
		break;
	case '4':
		tableId = 'giayTable'
		if (giayFlag === true) {
			reloadTable();
		}
		break;
	case '5':
		tableId = 'otherTable'
		if (phukienFlag === true) {
			reloadTable();
		}
		break;
	default:
		break;
	}
}