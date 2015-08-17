/**
 * Created by Tobi on 02.04.2015.
 */
var World = {
    loaded: false,

    init: function initFn(data) {
        this.createOverlays(data);
    },

    createOverlays: function createOverlaysFn(data) {

        var tracker = new AR.ClientTracker("ar.wtc", {
            onLoaded: this.worldLoaded
        });


        $.each(data, function(idx, sensor) {

            /*
                Image Button
             */
            var imgButton = new AR.ImageResource("images/button.png");

            /*
                Image Options
             */
            var options = {};
            options.onClick = function() {
                AR.context.openInBrowser(sensor.url);
            };

            options.offsetX = sensor.offsetX;
            options.offsetY = sensor.offsetY;
            options.zOrder = sensor.zOrder;

            /*
                Image Button Register on Display
             */
            var pageButton =  new AR.ImageDrawable(imgButton, sensor.size, options);

            var infoBox = new AR.HtmlDrawable({
                html:
                    '<div class="box">' +
                        '<h1>' + sensor.name + '</h1>' +
                        '<p>' + sensor.description + '</p>' +
                    '</div>'
            }, 1, {
                //viewportWidth: 500,
                //viewportHeight: 1024,
                backgroundColor: "#00FFFF",
                offsetX: 0.0,
                offsetY: 0.0,
                opacity : 1.0,
                //horizontalAnchor: AR.CONST.HORIZONTAL_ANCHOR.RIGHT,
                //verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                //clickThroughEnabled: true,
                allowDocumentLocationChanges: false
                //onDocumentLocationChanged: function onDocumentLocationChangedFn(uri) {
                //    AR.context.openInBrowser(uri);
                //}
            });

            /*
                Draw
             */
            new AR.Trackable2DObject(tracker, sensor.name, {
                drawables: {
                    cam: [pageButton/*, infoBox*/]
                }
            });

        });

    },

    worldLoaded: function worldLoadedFn() {
        var cssDivLeft = " style='display: table-cell;vertical-align: middle; text-align: right; width: 50%; padding-right: 15px;'";
        var cssDivRight = " style='display: table-cell;vertical-align: middle; text-align: left;'";
        document.getElementById('loadingMessage').innerHTML =
            "<div" + cssDivLeft + ">Suche Sensoren im Oma-Raum!</div>";
            /*"<div" + cssDivRight + "><img src='images/surfer.png' /></div>"*/;

        // Remove Scan target message after 10 sec.
        setTimeout(function() {
            var e = document.getElementById('loadingMessage');
            e.parentElement.removeChild(e);
        }, 5 * 1000);
    }
};

//Load Config
$.getJSON('config.json', function(data) {
    World.init(data);
});