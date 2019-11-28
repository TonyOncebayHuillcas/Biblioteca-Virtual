'use strict'

const Tema = require('../Modelos/Tema')

function getTema(req,res){
    let temaId = req.params.idTema
    Tema.findById(temaId, (err, tema)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        if (!tema) return res.status(404).send({message : 'El tema no existe'})
        res.status(200).send({tema: tema})
    })
}

function getTemas(req,res){
    Tema.find({}, (err, temas)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        if (!temas) return res.status(404).send({message : 'No hay temas'})
        res.status(200).send({temas:temas})
    })
}

function updateTema(req,res){
    let temaId = req.params.idTema
    let datos = req.body
    Tema.findByIdAndUpdate(temaId,datos, (err, temaActualizado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        res.status(200).send({tema:temaActualizado})
    })
}

function deleteTema(req,res){
    let temaId = req.params.idTema
    Tema.findById(temaId, (err, temaSolicitado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        
        temaSolicitado.remove(err=>{
            if (err) return res.status(500).send({message: 'Error al borrar el tema'})
            res.status(200).send({message:'El tema fue eliminado'})
        })
    })
}

function insertTema(req,res){
    let tema= new Tema()
    tema.nombre=req.body.nombre
    tema.save((err,temaGuardado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        res.status(200).send({tema:temaGuardado})
    })
}

module.exports = {
    getTema,
    getTemas,
    updateTema,
    deleteTema,
    insertTema
}

