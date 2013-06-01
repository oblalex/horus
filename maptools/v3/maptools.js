$(function () {
        /* declarations */
        var MAX_LETTERS = 26;
        var CELL_SIZE = 100;
        var is_big_map = false;

        var canvas_height = document.createElement('canvas');
        var context_height = canvas_height.getContext("2d");

        var areas = new Kinetic.Group();
        var scrollbars = new Kinetic.Group();
        var stage = new Kinetic.Stage({
                container: "container",
                width: 300,
                height: 300
            });
        var container = stage.getContainer();
        var layer = new Kinetic.Layer();
        var image = new Kinetic.Image();
        var img_grp = new Kinetic.Group();
        var current_cell = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: CELL_SIZE,
                height: CELL_SIZE,
                opacity: 0.2,
                fill: "#FF0",
                stroke: "black",
                strokeWidth: 2
            });
        stage.add(layer);
        /* functions */

        function squareName(x) {
            var k = Math.floor(x / 10000);
            var name = '';
            if (is_big_map) {
                var periods = Math.floor(k / MAX_LETTERS)
                name += String.fromCharCode(65 + periods);
            }
            name += String.fromCharCode(65 + (k % MAX_LETTERS));
            return name;
        }

        function squareNumber(y) {
            return Math.floor(y / 10000) + 1;
        }

        function squareLabel(x, y) {
            return squareName(x) + ":" + squareNumber(y);
        }

        function update_curent_info(x, y) {
            var offX = (img_grp.getOffsetX() % CELL_SIZE);
            var offY = (img_grp.getOffsetY() % CELL_SIZE);

            var ox = x + offX;
            var oy = y - $("#content").position().top + offY - 3;

            if (ox >= map_image.width || oy >= map_image.height) return;

            var px = (Math.floor(ox / CELL_SIZE) * CELL_SIZE) - offX;
            var py = (Math.floor(oy / CELL_SIZE) * CELL_SIZE) - offY;

            var dx = map_image.width - img_grp.getOffsetX() - px;
            if (dx < CELL_SIZE) {
                current_cell.setWidth(dx - 2);
            } else {
                current_cell.setWidth(CELL_SIZE);
            }
            var dy = map_image.height - img_grp.getOffsetY() - py;
            if (dy < CELL_SIZE) {
                current_cell.setHeight(dy);
            } else {
                current_cell.setHeight(CELL_SIZE);
            }

            current_cell.setPosition(px, py);
            layer.draw();

            var map_x = Math.floor(x + img_grp.getOffsetX()) * CELL_SIZE;
            var map_y = (map_image.height - Math.floor(y + img_grp.getOffsetY() - $("#content").position().top - 2)) * CELL_SIZE;

            $("#position_holder").text("x: " + map_x + "; y: " + map_y);
            $("#square_holder").text(squareLabel(map_x, map_y));

            var pixel = context_height.getImageData(map_x/(2*CELL_SIZE), heigh_image.height-(map_y/(2*CELL_SIZE)), 1, 1).data;
            $("#height_holder").text(intensityToHeight(pixel[0]) + " m");
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
        /* horizontal scrollbars */
        var hscrollArea = new Kinetic.Rect({
                x: 10,
                y: stage.intensityToHeighteight() - 30,
                width: stage.getWidth() - 40,
                height: 20,
                fill: 'black',
                opacity: 0.3
            });
        var hscroll = new Kinetic.Rect({
                x: 10,
                y: stage.intensityToHeighteight() - 30,
                width: 130,
                height: 20,
                fill: '#888',
                draggable: true,
                dragBoundFunc: function (pos) {
                    var newX = pos.x;
                    if (newX < 10) {
                        newX = 10;
                    } else if (newX > stage.getWidth() - 160) {
                        newX = stage.getWidth() - 160;
                    }
                    return {
                        x: newX,
                        y: this.getAbsolutePosition().y
                    }
                },
                opacity: 0.9,
                stroke: 'black',
                strokeWidth: 1
            });
        /* vertical scrollbars */
        var vscrollArea = new Kinetic.Rect({
                x: stage.getWidth() - 30,
                y: 10,
                width: 20,
                height: stage.intensityToHeighteight() - 40,
                fill: 'black',
                opacity: 0.3
            });
        var vscroll = new Kinetic.Rect({
                x: stage.getWidth() - 30,
                y: 10,
                width: 20,
                height: 70,
                fill: '#888',
                draggable: true,
                dragBoundFunc: function (pos) {
                    var newY = pos.y;
                    if (newY < 10) {
                        newY = 10;
                    } else if (newY > stage.intensityToHeighteight() - 100) {
                        newY = stage.intensityToHeighteight() - 100;
                    }
                    return {
                        x: this.getAbsolutePosition().x,
                        y: newY
                    }
                },
                opacity: 0.9,
                stroke: 'black',
                strokeWidth: 1
            });
        /* background update */
        var updateBackgroundPos = function () {
            var x = (hscroll.getPosition().x - 10) * ((image.getWidth() - hscrollArea.getWidth()) / hscrollArea.getWidth());
            var y = (vscroll.getPosition().y - 10) * ((image.intensityToHeighteight() - vscrollArea.intensityToHeighteight()) / vscrollArea.intensityToHeighteight());
            update_curent_info(0, $("#content").position().top);
            img_grp.setOffset(x, y);
        };
        hscroll.on('dragmove', updateBackgroundPos);
        vscroll.on('dragmove', updateBackgroundPos);
        /* scrollbars */
        scrollbars.on('mouseover', function () {
                document.body.style.cursor = 'pointer';
            });
        scrollbars.on('mouseout', function () {
                document.body.style.cursor = 'default';
            });
        layer.on('mousemove', function (e) {
                update_curent_info(e.x, e.y);
            });
        /* image */
        var map_image = new Image();
        var heigh_image = new Image();
        map_image.onload = function () {
            stage.setWidth($(window).width());
            stage.setHeight($("footer").position().top - $("#content").position().top - 3);
            areas.removeChildren();
            scrollbars.removeChildren();
            img_grp.removeChildren();
            layer.removeChildren();
            var is_bigger_w = stage.getWidth() < map_image.width;
            var is_bigger_h = stage.intensityToHeighteight() < map_image.height;
            var is_big_map = Math.floor(map_image.width / CELL_SIZE) > MAX_LETTERS;
            img_grp.setOffset(0, 0);
            image.setSize(map_image.width, map_image.height);
            image.setImage(map_image);
            img_grp.add(image);
            /* Draw grid verticals */
            var l = new Kinetic.Line({
                    stroke: "#777",
                    strokeWidth: 1,
                    points: [map_image.width - 2, 0, map_image.width - 2, map_image.height]
                });
            img_grp.add(l);
            var l = new Kinetic.Line({
                    stroke: "#777",
                    strokeWidth: 1,
                    points: [2, 0, 2, map_image.height]
                });
            img_grp.add(l);
            for (i = 0; i < map_image.width; i += CELL_SIZE) {
                var l = new Kinetic.Line({
                        stroke: "#777",
                        strokeWidth: 1,
                        points: [i, 0, i, map_image.height - 1]
                    });
                img_grp.add(l);
            }
            /* Draw grid horizontals */
            var l = new Kinetic.Line({
                    stroke: "#777",
                    strokeWidth: 1,
                    points: [0, map_image.height, map_image.width - 2, map_image.height]
                });
            img_grp.add(l);
            var l = new Kinetic.Line({
                    stroke: "#777",
                    strokeWidth: 1,
                    points: [0, 1, map_image.width - 2, 1]
                });
            img_grp.add(l);
            for (i = 0; i < map_image.height; i += CELL_SIZE) {
                var l = new Kinetic.Line({
                        stroke: "#777",
                        strokeWidth: 1,
                        points: [0, i, map_image.width - 1, i]
                    });
                img_grp.add(l);
            }
            layer.add(img_grp);
            layer.add(current_cell);
            /* Draw scrolls */
            vscroll.setX(0);
            hscroll.setY(0);
            if (is_bigger_w) {
                hscrollArea.setY(stage.intensityToHeighteight() - 30);
                hscrollArea.setWidth(stage.getWidth() - 40);
                hscroll.setY(stage.intensityToHeighteight() - 30);
                areas.add(hscrollArea);
                scrollbars.add(hscroll);
            }
            if (is_bigger_h) {
                vscrollArea.setX(stage.getWidth() - 30);
                vscrollArea.setHeight(stage.intensityToHeighteight() - 40);
                vscroll.setX(stage.getWidth() - 30);
                areas.add(vscrollArea);
                scrollbars.add(vscroll);
            }
            if (is_bigger_w || is_bigger_h) {
                layer.add(areas);
                layer.add(scrollbars);
            }
            layer.draw();
            heigh_image.src = map_image.src.replace("Map.png", "Map_h.png");
        };
        heigh_image.onload = function () {
            canvas_height.width  = heigh_image.width;
            canvas_height.height = heigh_image.height;
            context_height.drawImage(heigh_image, 0, 0, heigh_image.width, heigh_image.height);
            $("#display_loading").css('visibility', 'hidden');
        };
        $("#image_chooser").change(function () {
                var prefix = "../Maps/" + $(this).val();
                map_image.src = prefix + "/Map.png";
                $("#display_loading").css('visibility', 'visible');
            });
        $("#image_chooser").trigger('change');
    });
