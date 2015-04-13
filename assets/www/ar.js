/**
 * Created by Tobi on 02.04.2015.
 */
var World = {
    loaded: false,

    init: function initFn() {
        this.createOverlays();
    },

    createOverlays: function createOverlaysFn() {
        /*
         First an AR.ClientTracker needs to be created in order to start the recognition engine. It is initialized with a URL specific to the target collection. Optional parameters are passed as object in the last argument. In this case a callback function for the onLoaded trigger is set. Once the tracker is fully loaded the function worldLoaded() is called.
         Important: If you replace the tracker file with your own, make sure to change the target name accordingly.
         Use a specific target name to respond only to a certain target or use a wildcard to respond to any or a certain group of targets.
         */
        this.tracker = new AR.ClientTracker("ar.wtc", {
            onLoaded: this.worldLoaded
        });

        this.imgButton = new AR.ImageResource("images/imageOne.png");

        //*** 1
        /*var imgOne = new AR.ImageResource("images/imageOne.png");
        var overlayOne = new AR.ImageDrawable(imgOne, 1, {
            offsetX: -0.50,
            offsetY: 0
        });*/

        var pageOneButton = this.createWwwButton("http://n1sco.com/specifications/", 1, {
            offsetX: -0.25,
            offsetY: -0.25,
            zOrder: 1
        });
        new AR.Trackable2DObject(this.tracker, "i5", {
            drawables: {
                cam: [pageOneButton]
            }
        });

        //*** 2
        var imgTwo = new AR.ImageResource("images/boot.png");
        var overlayTwo = new AR.ImageDrawable(imgTwo, 1, {
            offsetX: -0.50,
            offsetY: 0
        });
        new AR.Trackable2DObject(this.tracker, "saskia", {
            drawables: {
                cam: overlayTwo
            }
        });
    },

    createWwwButton: function createWwwButtonFn(url, size, options) {
        options.onClick = function() {
            AR.context.openInBrowser(url);
        };
        return new AR.ImageDrawable(this.imgButton, size, options);
    },

    worldLoaded: function worldLoadedFn() {
        var cssDivLeft = " style='display: table-cell;vertical-align: middle; text-align: right; width: 50%; padding-right: 15px;'";
        var cssDivRight = " style='display: table-cell;vertical-align: middle; text-align: left;'";
        document.getElementById('loadingMessage').innerHTML =
            "<div" + cssDivLeft + ">Scan Target &#35;1 (surfer):</div>" +
            "<div" + cssDivRight + "><img src='images/surfer.png' /></div>";

        // Remove Scan target message after 10 sec.
        setTimeout(function() {
            var e = document.getElementById('loadingMessage');
            e.parentElement.removeChild(e);
        }, 10000);
    }
};

World.init();