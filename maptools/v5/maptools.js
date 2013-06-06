var MAX_LETTERS = 26
    , CELL_SIDE = 100
    , MILE_COEF = 0.621371
    , FOOT_COEF = 3.28084
    , kilometers_koef = 1000
    , is_wide_map = false
    , is_imperial_system = false
    , map_canvas = null
    , map_ctx = null
    , height_canvas
    , height_ctx
    , map_img = new Image()
    , height_img = new Image()
    , map_path = null
    , current_pos_x = 0
    , current_pos_y = 0
    , current_height = 0;


function initUI(){
    $(document).tooltip();
    $('#map_selector').chosen().trigger('change');
}

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

function drawStroked(text, type, x, y) {
    var font_size = 12+(type-1)*2.5;

    map_ctx.font = font_size + "px Sans-serif";
    map_ctx.lineWidth = 2;

    var metrics = map_ctx.measureText(text);

    x -= metrics.width/2;
    y += font_size/2;

    if (x<3) x=3;
    if(x+metrics.width > map_img.width) x = map_img.width-metrics.width-3;

    map_ctx.strokeStyle = 'white';
    map_ctx.strokeText(text, x, y);
    map_ctx.fillStyle = 'black';
    map_ctx.fillText(text, x, y);
}

function drawMapData(){
    $.get(map_path + "Props.xml", {}, function (xml){
        $('MapText', xml).each(function(i){
            drawStroked(
                $(this).attr('NameEng')
                , $(this).attr('Type')
                , $(this).attr('X')/CELL_SIDE
                , map_img.height-($(this).attr('Y')/CELL_SIDE));
        });
    });
}

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

function intensityToHeight(intensity) {
    if (intensity < 64) return intensity;
    else if (intensity < 96) return 64 + (intensity - 64) * 2;
    else if (intensity < 128) return 128 + (intensity - 96) * 4;
    else if (intensity < 160) return 256 + (intensity - 128) * 8;
    else if (intensity < 192) return 512 + (intensity - 160) * 16;
    else if (intensity < 224) return 1024 + (intensity - 192) * 32;
    else return 2048 + (intensity - 224) * 64;
}

function displayMapSize(){
    $("#map_width").text((map_canvas.width*CELL_SIDE/kilometers_koef).toFixed(2));
    $("#map_height").text((map_canvas.height*CELL_SIDE/kilometers_koef).toFixed(2));
}

function displayCurrentPos(){
    $("#current_pos_x").text((current_pos_x/kilometers_koef).toFixed(2));
    $("#current_pos_y").text((current_pos_y/kilometers_koef).toFixed(2));
}

function displayCurrentSquare(){
    $("#current_cell_letter").text(squareName(current_pos_x));
    $("#current_cell_number").text(squareNumber(current_pos_y));
}

function displayCurrentHeight(){
    var h = current_height;
    if (is_imperial_system){
        h *= FOOT_COEF;
    }

    $("#current_height").text((h).toFixed(2));
}

function displayData(){
    displayMapSize();
    displayCurrentPos();
    displayCurrentHeight();
}

$(function () {
    map_canvas = document.getElementById("container");
    map_ctx = map_canvas.getContext("2d");
    height_canvas = document.createElement('canvas');
    height_ctx = height_canvas.getContext("2d");

    $(map_canvas).mousemove(function(e){
        current_pos_x = e.clientX + (window.pageXOffset || document.documentElement.scrollLeft)
        , current_pos_y = e.clientY - $("#placeholder").position().top + 1 + (window.pageYOffset || document.documentElement.scrollTop);

        var pixel = height_ctx.getImageData(current_pos_x/2, current_pos_y/2, 1, 1).data;
        current_height = intensityToHeight(pixel[0]);
        displayCurrentHeight();

        current_pos_y = map_img.height-current_pos_y;
        displayCurrentSquare();

        current_pos_x *= CELL_SIDE;
        current_pos_y *= CELL_SIDE;
        displayCurrentPos();
    });

    map_img.onload = function (){
        height_img.src = map_path + "Map_h.png";

        /* Init canvas and containers geometry */
        map_canvas.width = this.width;
        map_canvas.height = this.height;
        map_canvas.style.top = 0 + "px";
        map_canvas.style.left = 0 + "px";

        is_wide_map = Math.floor(this.width / CELL_SIDE) > MAX_LETTERS;

        /* Draw image */
        map_ctx.drawImage(this, 0, 0, this.width, this.height);
        drawBoard(map_canvas.width, map_canvas.height, 0);
        drawMapData();
        displayMapSize();
    };

    height_img.onload = function () {
        height_canvas.width  = height_img.width;
        height_canvas.height = height_img.height;
        height_ctx.drawImage(height_img, 0, 0, height_img.width, height_img.height);
        $("#display_loading").css('visibility', 'hidden');
    };

    $("#map_selector").change(function () {
        map_path = "../Maps/" + $(this).val() + "/";
        map_img.src = map_path + "Map.png";
        $("#display_loading").css('visibility', 'visible');
    });

    $('#slideOne').change(function () {
        is_imperial_system = $(this).is(':checked');

        kilometers_koef = 1000;

        if (is_imperial_system){
            kilometers_koef /= MILE_COEF;
            $('.metrics').text("ft");
            $('.metrics_k').text("mil");
        } else {
            $('.metrics').text("m");
            $('.metrics_k').text("km");
        }

        displayData();
    });

    initUI();
});
