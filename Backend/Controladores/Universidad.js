'use strict'

const Universidad = require('../Modelos/Universidad')

function getUniversidad(req,res){
    let universidadId = req.params.idUniversidad
    Universidad.findById(universidadId, (err, universidad)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion get'})
        if (!universidad) return res.status(404).send({message : 'La universidad no  existe'})
        res.status(200).send({universidad: universidad})
    })
}

function getUniversidades(req,res){
    Universidad.find({}, (err, universidades)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion gets'})
        if (!universidades) return res.status(404).send({message : 'No hay Universidades'})
        res.status(200).send({universidades:universidades})
    })
}

function updateUniversidad(req,res){
    let universidadId = req.params.idUniversidad
    let datos = req.body
    Universidad.findByIdAndUpdate(universidadId,datos, (err, universidadActualizado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion update'})
        res.status(200).send({universidad:universidadActualizado})
    })
}

function deleteUniversidad(req,res){
    let universidadId = req.params.idUniversidad
    
    Universidad.findById(universidadId, (err, universidadSolicitado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion delete'})
        
        universidadSolicitado.remove(err=>{
            if (err) return res.status(500).send({message: 'Error al borrar la universidad'})
            res.status(200).send({message:'El universidad fue eliminada'})
        })
    })
}

function insertUniversidad(req,res){
    let universidad= new Universidad()
    universidad.nombre=req.body.nombre
    universidad.imagen=req.body.imagen
    
    console.log('universidad:', req.body);

    universidad.save((err,universidadGuardado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion insert'})
        res.status(200).send({universidad:universidadGuardado})
    })
}

module.exports = {
    getUniversidad,
    getUniversidades,
    updateUniversidad,
    deleteUniversidad,
    insertUniversidad
}
