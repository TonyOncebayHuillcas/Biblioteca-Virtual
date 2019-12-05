'use strict'
const base64 = require('base64topdf');
const Documento = require('../Modelos/Documento')
const fs = require('fs')


function getDocumento(req,res){
    let documentoId = req.params.idDocumento
    Documento.findById(documentoId, (err, documento)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        if (!documento) return res.status(404).send({message : 'El documento no existe'})
        res.status(200).send({documento: documento})
    })
}

function getDocumentos(req,res){
    /* Documento.find({}, (err, documentos)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        if (!documentos) return res.status(404).send({message : 'No hay documentos'})
        res.status(200).send({documentos:documentos})
    }) */

    Documento.find({}, 'nombre contador fecha codTema codUsuario', function (err, documento) {
        var docs=[]
        for (let a of documento){
          var doc ={
              nombre:'',
              contador:'',
              fecha:'',
              codTema:'',
              codUsuario:'',
              id:''
          }
          doc.nombre=a.nombre
          doc.contador=a.contador
          doc.fecha=a.fecha
          doc.codTema=a.codTema
          doc.codUsuario=a.codUsuario
          doc.id=a._id
          docs.push(doc)
        }
        res.status(200).send({ estado: 'ok', documentos: docs }) 
    })
}

function updateDocumento(req,res){
    let documentoId = req.params.idDocumento
    let datos = req.body
    Documento.findByIdAndUpdate(documentoId,datos, (err, documentoActualizado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        res.status(200).send({documento:documentoActualizado})
    })
}

function deleteDocumento(req,res){
    let documentoId = req.params.idDocumento
    Documento.findById(documentoId, (err, documentoSolicitado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        
        documentoSolicitado.remove(err=>{
            if (err) return res.status(500).send({message: 'Error al borrar el documento'})
            res.status(200).send({message:'El documento fue eliminado'})
        })
    })
}

function insertDocumento(req,res){
    //console.log('Aqui', req.body)
    //console.log(req.body.codigoBase64Redu)
    
    // var nombrePDF = ''+req.body.nombre
    // var nameSpace = nombrePDF.replace(/ /g,'')
    // var nombrePDF = './PDF/' +  nameSpace
    let documento= new Documento()

    // let decodedBase64 = base64.base64Decode(req.body.codigoBase64Redu, nombrePDF);
    
    documento.nombre=req.body.nombre
    documento.contador=req.body.contador
    documento.fecha=req.body.fecha
    documento.codUsuario=req.body.codUsuario
    documento.codTema=req.body.codTema
    documento.archivo=req.body.codigoBase64Redu
    console.log('lego aqui', req.body.codigoBase64Redu)
    documento.save((err,documentoGuardado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        res.status(200).send({documento:documentoGuardado})
    })
    
}

function retornarDocumento(req,res){
    //console.log('Entro aqui')
    //console.log('Aqui es: ', req.params.nombre)
    
    //var nombre=''+req.params.nombre+'.pdf'
    let documentoId = req.params.idDocumento

    Documento.findById(documentoId, (err, documentoSolicitado)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la peticion'})
        //var nombrePDF = ''+req.body.nombre
    // var nameSpace = nombrePDF.replace(/ /g,'')
     var nombrePDF = './PDF/' +  documentoSolicitado._id

        let decodedBase64 = base64.base64Decode(documentoSolicitado.archivo,nombrePDF);
        var filePath = './PDF/' + documentoSolicitado._id;
        //var rutaNueva = __dirname.substring(0, )
        //console.log(filePath)
        fs.readFile(filePath , (err,data) => {
            res.contentType("application/pdf");
            res.send(data);
        });
        //res.status(200).send({estado:'ok', nombre:documentoSolicitado.nombre})
    })
}

function getDocumentoByUser(req, res) {
    var userID = req.params.userID
    
    Documento.find({ 'codUsuario': userID }, 'codUsuario contador nombre', function (err, doc) {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion insertar' })
        if (doc != undefined) {
            res.status(200).send({ estado: 'ok', documentos: doc})
        }
        else {
            res.status(200).send({ estado: 'error' })
        }
    })
}

module.exports = {
    getDocumento,
    getDocumentos,
    updateDocumento,
    deleteDocumento,
    insertDocumento,
    retornarDocumento,
    getDocumentoByUser
}
