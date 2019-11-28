'use strict'

const express = require('express')
const api = express.Router()

const documentoCtrl = require('../Controladores/Documento')

api.get('/documento', documentoCtrl.getDocumentos)
api.get('/documento/:idDocumento', documentoCtrl.getDocumento)
api.post('/documento', documentoCtrl.insertDocumento)
api.put('/documento/:idDocumento', documentoCtrl.updateDocumento)
api.delete('/documento/:idDocumento', documentoCtrl.deleteDocumento)
api.get('/documentoPDF/:idDocumento', documentoCtrl.retornarDocumento)
api.get('/documentoByUser/:userID', documentoCtrl.getDocumentoByUser)


module.exports = api