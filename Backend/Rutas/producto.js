'use strict'

const express = require('express')
const api = express.Router()

const productCtrl = require('../Controladores/producto')

api.get('/product', productCtrl.getProducts)
api.get('/product/:idProduct', productCtrl.getProduct)
api.post('/product', productCtrl.insertProduct)
api.put('/product/:idProduct', productCtrl.updateProduct)
api.delete('/product/:idProduct', productCtrl.deleteProduct)

/*
api.get('/private',auth.isAuth, (req, res)=>{
    res.status(200).send({Message: 'Tienes acceso'})
})
*/

module.exports = api