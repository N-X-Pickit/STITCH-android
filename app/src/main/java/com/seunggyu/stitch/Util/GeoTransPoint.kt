package com.seunggyu.stitch.Util

class GeoTransPoint {
    var x = 0.0
    var y = 0.0
    var z = 0.0

    /**
     *
     */
    constructor() : super() {}

    /**
     * @param x
     * @param y
     */
    constructor(x: Double, y: Double) : super() {
        this.x = x
        this.y = y
        z = 0.0
    }

    /**
     * @param x
     * @param y
     * @param y
     */
    constructor(x: Double, y: Double, z: Double) : super() {
        this.x = x
        this.y = y
        this.z = 0.0
    }
}