(function ($, window, document) {
    $( function(){
        
        // properties
        var curDate = new Date();
        var authorsBaseUrl = "AuthorController";
        
        var $document = $(document);
        var $body = $('body');
        var $authorTableBody = $('#authorTableBody');
        
        $document.ready(function(){
            
            alert("document ready event fired!");
            
            // Make sure we only do this on pages with an author list
            if ($body.attr('class') === 'authorList') {
                $.ajax({
                    type: 'GET',
                    url: authorsBaseUrl + "?action=listAjax",
                    success: function (authors) {
                        displayAuthors(authors);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get authors for this user due to: " + errorThrown.toString());
                    }
                });
            }
        });
        
        /*         
         * Loops over the authors collection returned by the server and 
         * extracts individual author objects and their properties, then 
         * builds table rows and columns using this data. Each row is 
         * dynamically appended to the table body DOM element.
         */
        function displayAuthors(authors) {
            $.each(authors, function (index, author) {
                var row = '<tr class="authorListRow">' +
                        '<td>' + author.authorId + '</td>' +
                        '<td>' + author.authorName + '</td>' +
                        '<td>' + author.dateAdded + '</td>' +
                        '</tr>';
                $authorTableBody.append(row);
            });
        }
        
        $authorTableBody.on('click', 'tr', function () {
            console.log('row click event fired');
            var authorId = $(this).find("td").contents()[0].data;
            console.log(authorId);
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: authorsBaseUrl + "?action=findByIdAjax",
                dataType: "json",
                data: JSON.stringify({"authorId": authorId}),
                success: function (author) {
                    $('#addEditAuthor').show();
                    $('#authorId').val(author.authorId);
                    $('#authorName').val(author.authorName);
                    $('#dateAdded').val(author.dateAdded);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("Could not get author by id due to: " + errorThrown.toString());
                }
            });
        });

        $authorTableBody.on('mouseover', 'tr', function () {
            console.log('row mouseover event fired');
            $(this).toggleClass('hover');
        });

        $authorTableBody.on('mouseout', 'tr', function () {
            console.log('row mouseout event fired');
            $(this).toggleClass('hover');
        });
        
        
    });
    
}(window.jQuery, window, document));


