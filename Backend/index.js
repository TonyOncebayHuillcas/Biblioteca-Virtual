'use strict'

const mongoose = require('mongoose')
const app = require('./app')
const config = require('./config')

mongoose.connect(config.db, (err, res)=>{
    useCreateIndex: true
    useNewUrlParser: true
    if (err) throw err
    console.log("Conexion con la base de datos establecida")
    app.listen(config.port, ()=>{
        console.log('Api rest corriendo prueba ')
    })
})

