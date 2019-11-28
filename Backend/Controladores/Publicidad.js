'use strict'

const Publicidad = require('../Modelos/Publicidad')

function getPublicidad(req,res){
    let publicidadId = req.params.idPublicidad
    Publicidad.findById(publicidadId, (err, publicidad)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        if (!publicidad) return res.status(404).send({message : 'Tal publicidad no existe'})
        res.status(200).send({publicidad: publicidad})
    })
}

function getPublicidades(req,res){
    Publicidad.find({}, (err, publicidades)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        if (!publicidades) return res.status(404).send({message : 'No hay publicidades'})
        res.status(200).send({publicidades:publicidades})
    })
}

function updatePublicidad(req,res){
    let publicidadId = req.params.idPublicidad
    let datos = req.body
    Publicidad.findByIdAndUpdate(publicidadId,datos, (err, publicidadActualizado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        res.status(200).send({publicidad:publicidadActualizado})
    })
}

function deletePublicidad(req,res){
    let publicidadId = req.params.idPublicidad
    Publicidad.findById(publicidadId, (err, publicidadSolicitado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        
        publicidadSolicitado.remove(err=>{
            if (err) return res.status(500).send({message: 'Error al borrar la publicidad'})
            res.status(200).send({message:'La publicidad fue eliminada'})
        })
    })
}

function insertPublicidad(req,res){
    let publicidad = new Publicidad()
    
    publicidad.nombre=req.body.nombre
    publicidad.direccion=req.body.direccion
    publicidad.codUsuario=req.body.codUsuario
    publicidad.codUni=req.body.codUni
    
    
    publicidad.save((err,publicidadGuardada)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        res.status(200).send({publicidad:publicidadGuardada})
    })
}


module.exports = {
    getPublicidad,
    getPublicidades,
    updatePublicidad,
    deletePublicidad,
    insertPublicidad
}
