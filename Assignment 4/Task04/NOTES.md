# Q4 – Event-handling forensics

The original handler used `mouseclicked` rather than the exact `MouseListener` method `mouseClicked`; Java is case-sensitive, so the method did not override the interface method. Consequently no click callback ran. The original code also did not update the label and drew directly through `getGraphics()`, which is temporary and not retained by AWT.

The completed program stores each click as a `Point`, updates the label, calls `repaint()`, and redraws every point in `paint()`. Reproduce persistence: click several times, minimize the window, then restore it. The points remain because they are model data and paint is called again. `@Override` makes a misspelled method a compile-time error. Remove the annotation and rename the method `mouseclicked`: it compiles as an unrelated method but is never called.

Alternative: draw to a `BufferedImage` and copy the image during paint. Repainting n stored points costs O(n); copying a pre-rendered image is O(width×height) pixel copy (often optimized by graphics hardware), while point storage is O(n) objects and image storage is O(width×height) pixels × bytes-per-pixel. The image approach is useful for many marks or raster effects; point storage is easier to edit/undo.
