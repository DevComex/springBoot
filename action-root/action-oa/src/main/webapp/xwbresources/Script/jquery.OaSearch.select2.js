/*
by chenqiaoling 2014/06/10
Version: v1.0

*/
$(function () {


    var data = [];
    $.ajax({
        url: "http://oa.gyyx.cn/staff/GetStaffName?name=s",
        type: "get",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: {
            r: Math.random()
        },
        success: function (d) {
            data = d;
        }
    });
    console.log(data);

    if ($.fn.select2) {
        $("#select2-tags").select2({
            //data:{results:data, text:'RealName'},
            tags: data,
            ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
                url: "http://oa.gyyx.cn/staff/GetStaffName?name=s",
                dataType: 'jsonp',
                data: function (term, page) {
                    return {
                        q: term, // search term
                        page_limit: 10,
                        apikey: "ju6z9mjyajq2djue3gbvv26t" // please do not use so this example keeps working
                    };
                },
                results: function (data, page) { // parse the results into the format expected by Select2.
                    // since we are using custom formatting functions we do not need to alter remote JSON data
                    return { results: data.movies };
                }
            },
            initSelection: function (element, callback) {
                // the input tag has a value attribute preloaded that points to a preselected movie's id
                // this function resolves that id attribute to an object that select2 can render
                // using its formatResult renderer - that way the movie name is shown preselected
                var id = $(element).val();
                if (id !== "") {
                    $.ajax("http://oa.gyyx.cn/staff/GetStaffName?name=" + id, {
                        data: {
                            apikey: "ju6z9mjyajq2djue3gbvv26t"
                        },
                        dataType: "jsonp"
                    }).done(function (data) { callback(data); });
                }
            }
            //formatResult: movieFormatResult, // omitted for brevity, see the source of this page
            //formatSelection: movieFormatSelection,  // omitted for brevity, see the source of this page
            //dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
            //escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
        });

    }
});