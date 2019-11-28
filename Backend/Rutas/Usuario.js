'use strict'

const express = require('express')
const api = express.Router()

const usuarioCtrl = require('../Controladores/Usuario')

api.get('/usuario', usuarioCtrl.getUsuarios)
api.get('/usuario/:idUsuario', usuarioCtrl.getUsuario)
api.post('/usuario', usuarioCtrl.insertUsuario)
api.put('/usuario/:idUsuario', usuarioCtrl.updateUsuario)
api.delete('/usuario/:idUsuario', usuarioCtrl.deleteUsuario)
api.post('/loginUser', usuarioCtrl.loginUser)

module.exports = api