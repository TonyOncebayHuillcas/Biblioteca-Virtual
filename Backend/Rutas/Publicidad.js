'use strict'

const express = require('express')
const api = express.Router()

const publicidadCtrl = require('../Controladores/Publicidad')

api.get('/publicidad', publicidadCtrl.getPublicidades)
api.get('/publicidad/:idPublicidad', publicidadCtrl.getPublicidad)
api.put('/publicidad/:idPublicidad', publicidadCtrl.updatePublicidad)
api.delete('/publicidad/:idPublicidad', publicidadCtrl.deletePublicidad)
api.post('/publicidad', publicidadCtrl.insertPublicidad)

module.exports = api