'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const ProductoEsquema = Schema({
    name: String,
    picture: String,
    price:{type: Number, default:0},
    category: {
        type:String,
        enum: ['computadoras', 'telefonos', 'accesoarios']
    },
    description:String
})

module.exports = mongoose.model('Product', ProductoEsquema)
