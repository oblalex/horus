$(function () {
    var MAX_LETTERS = 26
    , SCROLL_BAR_PLACE = 20
    , CELL_SIDE = 100
    , is_wide_map = false
    , map_canvas = document.getElementById("container")
    , map_ctx = map_canvas.getContext("2d")
    , map_canvas_wrap = $("#container-wrapper")
    , height_canvas = document.createElement('canvas')
    , height_ctx = height_canvas.getContext("2d")
    , map_img = new Image()
    , height_img = new Image();

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

    function squareName(x) {
        var k = Math.floor(x / CELL_SIDE);
        var name = '';
        if (is_wide_map) {
            var periods = Math.floor(k / MAX_LETTERS)
            name += String.fromCharCode(65 + periods);
        }
        name += String.fromCharCode(65 + (k % MAX_LETTERS));
        return name;
    }

    function squareNumber(y) {
        return Math.floor(y / CELL_SIDE) + 1;
    }

    function squareLabel(x, y) {
        return squareName(x) + ":" + squareNumber(y);
    }

    function intensityToHeight(intensity) {
        if (intensity < 64) return intensity;
        else if (intensity < 96) return 64 + (intensity - 64) * 2;
        else if (intensity < 128) return 128 + (intensity - 96) * 4;
        else if (intensity < 160) return 256 + (intensity - 128) * 8;
        else if (intensity < 192) return 512 + (intensity - 160) * 16;
        else if (intensity < 224) return 1024 + (intensity - 192) * 32;
        else return 2048 + (intensity - 224) * 64;
    }

    $(map_canvas).mousemove(function(e){
        var x = e.clientX
        , offX = map_canvas.style.left
        , y = e.clientY-$("#content").position().top - 2
        , offY = map_canvas.style.top;

        if (offX){
            x -= parseInt(offX.replace("px", ""));
        }

        if (offY){
            y -= parseInt(offY.replace("px", ""));
        }

        var plainY = y;
        y = map_img.height-y;

        $("#square_holder").text(squareLabel(x, y));
        $("#position_holder").text("x: " + x*CELL_SIDE + "; y: " + y*CELL_SIDE);

        var pixel = height_ctx.getImageData(x/2, plainY/2, 1, 1).data;
        $("#height_holder").text(intensityToHeight(pixel[0]) + " m");
    });

    function drawBoard(bw, bh, p){
        for (var x = 0; x < bw; x += CELL_SIDE) {
            map_ctx.moveTo(0.5 + x + p, p);
            map_ctx.lineTo(0.5 + x + p, bh + p);
        }

        for (var y = bh; y > 0; y -= 100) {
            map_ctx.moveTo(p, 0.5 + y + p);
            map_ctx.lineTo(bw + p, 0.5 + y + p);
        }

        map_ctx.moveTo(0, bh-0.5);
        map_ctx.lineTo(bw-0.5, bh-0.5);
        map_ctx.lineTo(bw-0.5, 0);

        map_ctx.strokeStyle = "gray";
        map_ctx.stroke();
    }

    map_img.onload = function (){
        height_img.src = map_img.src.replace("Map.png", "Map_h.png");

        /* Init canvas and containers geometry */
        map_canvas.width = this.width;
        map_canvas.height = this.height;
        map_canvas.style.top = 0 + "px";
        map_canvas.style.left = 0 + "px";

        map_canvas_wrap.width($(window).width() - SCROLL_BAR_PLACE);
        map_canvas_wrap.height($("footer").position().top - $("#content").position().top-3 - SCROLL_BAR_PLACE);

        is_wide_map = Math.floor(this.width / CELL_SIDE) > MAX_LETTERS;

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

        $("#map_size_holder").text(map_canvas.height*CELL_SIDE+" x "+map_canvas.width*CELL_SIDE);

        /* Draw image*/
        map_ctx.drawImage(this, 0, 0, this.width, this.height);
        drawBoard(map_canvas.width, map_canvas.height, 0);
    };
    height_img.onload = function () {
        height_canvas.width  = height_img.width;
        height_canvas.height = height_img.height;
        height_ctx.drawImage(height_img, 0, 0, height_img.width, height_img.height);
        $("#display_loading").css('visibility', 'hidden');
    };

    $("#image_chooser").change(function () {
        map_img.src = "../Maps/" + $(this).val() + "/Map.png";
        $("#display_loading").css('visibility', 'visible');
    });
    $("#image_chooser").trigger('change');
    $('.chzn-select').chosen();
});
