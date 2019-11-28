'use strict'

const express = require('express')
const bodyParser = require('body-parser')

const app = express()
const api = require ('./Rutas/producto')
const tema = require ('./Rutas/Tema')
const usuario = require ('./Rutas/Usuario')
const documento = require ('./Rutas/Documento')
const publicidad = require ('./Rutas/Publicidad')
const universidad = require ('./Rutas/Universidad')

app.use(bodyParser.json({limit: '50mb',extended: true}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

app.use((req,res,next) => {
	res.header('Access-Control-Allow-Origin','*');
	res.header('Access-Control-Allow-Headers','Authorization, X-API-KEY ,x-access-token,Origin, X-Requested-With, Accept , Content-Type , Access-Control-Allow-Request-Method');
	res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE');
	res.header('Allow','GET, POST, OPTIONS, PUT, DELETE,x-access-token');
	next();
});

app.use('/api',api)
app.use('/api',tema)
app.use('/api',usuario)
app.use('/api',documento)
app.use('/api',publicidad)
app.use('/api',universidad)

module.exports = app