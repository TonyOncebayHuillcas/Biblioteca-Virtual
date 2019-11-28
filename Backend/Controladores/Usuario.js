'use strict'

const Usuario = require('../Modelos/Usuario')

function getUsuario(req, res) {
    let usuarioId = req.params.idUsuario
    Usuario.findById(usuarioId, (err, usuario) => {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion Get' })
        if (!usuario) return res.status(404).send({ message: 'El Usuario no existe' })
        res.status(200).send({ usuario: usuario })
    })
}

function getUsuarios(req, res) {
    Usuario.find({}, (err, usuarios) => {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion GEts' })
        if (!usuarios) return res.status(404).send({ message: 'No hay usuarios' })
        res.status(200).send({ usuarios: usuarios })
    })
}

function updateUsuario(req, res) {
    let usuarioId = req.params.idUsuario
    let datos = req.body
    Usuario.findByIdAndUpdate(usuarioId, datos, (err, usuarioActualizado) => {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion update' })
        res.status(200).send({ usuario: usuarioActualizado })
    })
}

function deleteUsuario(req, res) {
    let usuarioId = req.params.idUsuario

    Usuario.findById(usuarioId, (err, usuarioSolicitado) => {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion delte' })

        usuarioSolicitado.remove(err => {
            if (err) return res.status(500).send({ message: 'Error al borrar el usuario  remov' })
            res.status(200).send({ message: 'El usuario fue eliminado' })
        })
    })
}

function insertUsuario(req, res) {
    let usuario = new Usuario()

    usuario.userName = req.body.userName
    usuario.correo = req.body.correo
    usuario.contra = req.body.contra
    usuario.numCue = req.body.numCue
    usuario.codUni = req.body.codUni

    usuario.save((err, usuarioGuardado) => {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion insertar' })
        res.status(200).send({ usuario: usuarioGuardado })
    })
}

function loginUser(req, res) {
    var userN = req.body.userName
    var pass = req.body.contra

    Usuario.findOne({ 'userName': userN }, 'userName correo contra numCue codUni', function (err, person) {
        if (err) return res.status(500).send({ message: 'Error al realizar la peticion insertar' })
        if (person != undefined && pass == person.contra) {
            res.status(200).send({ estado: 'ok', userName: person.userName, numCue: person.numCue, codUni: person.codUni, _id: person._id })
        }
        else {
            res.status(200).send({ estado: 'error' })
        }
    })
}

module.exports = {
    getUsuario,
    getUsuarios,
    updateUsuario,
    deleteUsuario,
    insertUsuario,
    loginUser
}

