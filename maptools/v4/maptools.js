$(function () {
    var SCROLL_BAR_PLACE = 20
    , CELL_SIDE = 100
    , map_canvas = document.getElementById("container")
    , map_ctx = map_canvas.getContext("2d")
    , map_canvas_wrap = $("#container-wrapper")
    , map_img = new Image();

    $(".bar").draggable({
        containment: "parent"
    });

    $("#vbar").on("drag", function (event, ui) {
        var ctop = (-ui.position.top * map_canvas.height / map_canvas_wrap.height());
        map_canvas.style.top = ctop + "px"
    });

    $("#hbar").on("drag", function (event, ui) {
        var cleft = (-ui.position.left * map_canvas.width / map_canvas_wrap.width());
        map_canvas.style.left = cleft + "px"
    });

    function drawBoard(bw, bh, p){
        for (var x = 0; x < bw; x += CELL_SIDE) {
            map_ctx.moveTo(0.5 + x + p, p);
            map_ctx.lineTo(0.5 + x + p, bh + p);
        }

        for (var y = 0; y < bh; y += CELL_SIDE) {
            map_ctx.moveTo(p,0.5 + y + p);
            map_ctx.lineTo(bw + p,0.5 + y + p);
        }

        map_ctx.moveTo(0, bh-0.5);
        map_ctx.lineTo(bw-0.5, bh-0.5);
        map_ctx.lineTo(bw-0.5, 0);

        map_ctx.strokeStyle = "gray";
        map_ctx.stroke();
    }

    map_img.onload = function (){
        /* Init canvas and containers geometry */
        map_canvas.width = this.width;
        map_canvas.height = this.height;

        map_canvas_wrap.width($(window).width() - SCROLL_BAR_PLACE);
        map_canvas_wrap.height($("footer").position().top - $("#content").position().top-3 - SCROLL_BAR_PLACE);

        /* Init scrollbars */
        if(this.width > map_canvas_wrap.width()){
            var hscroll = $("#hscroll");
            hscroll.width(map_canvas_wrap.width()-(SCROLL_BAR_PLACE)/2);
            hscroll.css('visibility', 'visible');
            $("#hbar").width(hscroll.width()*hscroll.width()/this.width);
        } else {
            $("#hscroll").css('visibility', 'hidden');
        }

        if(this.height > map_canvas_wrap.height()){
            var vscroll = $("#vscroll");
            vscroll.height(map_canvas_wrap.height()-(SCROLL_BAR_PLACE)/2);
            vscroll.css('visibility', 'visible');
            $("#vbar").height(vscroll.height()*vscroll.height()/this.height);
        } else {
            $("#vscroll").css('visibility', 'hidden');
        }

        /* Draw image*/
        map_ctx.drawImage(this, 0, 0, this.width, this.height);
        drawBoard(map_canvas.width, map_canvas.height, 0);
    };
    map_img.src = "http://placehold.it/2350x1150";
});
