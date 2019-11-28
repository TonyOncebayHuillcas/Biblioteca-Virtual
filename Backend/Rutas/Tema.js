'use strict'

const express = require('express')
const api = express.Router()

const temaCtrl = require('../Controladores/Tema')

api.get('/tema', temaCtrl.getTemas)
api.get('/tema/:idTema', temaCtrl.getTema)
api.post('/tema', temaCtrl.insertTema)
api.put('/tema/:idTema', temaCtrl.updateTema)
api.delete('/tema/:idTema', temaCtrl.deleteTema)

module.exports = api