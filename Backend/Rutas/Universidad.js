'use strict'

const express = require('express')
const api = express.Router()

const uniCtrl = require('../Controladores/Universidad')

api.get('/universidad', uniCtrl.getUniversidades)
api.get('/universidad/:idUniversidad', uniCtrl.getUniversidad)
api.post('/universidad', uniCtrl.insertUniversidad)
api.put('/universidad/:idUniversidad', uniCtrl.updateUniversidad)
api.delete('/universidad/:idUniversidad', uniCtrl.deleteUniversidad)

module.exports = api