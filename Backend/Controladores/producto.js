'use strict'

const Product = require('../Modelos/producto')

function getProduct(req,res){
    let productId = req.params.idProduct
    Product.findById(productId, (err, product)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la pericion'})
        if (!product) return res.status(404).send({message : 'El producto no existe'})
        res.status(200).send({producto: product})
    })
}

function getProducts(req,res){
    Product.find({}, (err, products)=>{
        if (err) return res.status(500).send({message: 'Error al realizar la pericion'})
        if (!products) return res.status(404).send({message : 'No hay Productos'})
        res.status(200).send({productos:products})
    })
}

function updateProduct(req,res){
    let productId = req.params.idProduct
    let update = req.body
    Product.findByIdAndUpdate(productId,update, (err, update)=>{
        if (err) res.status(500).send({message: 'Error al actualizar'})
        res.status(200).send({product:update})
    })
}

function deleteProduct(req,res){
    let productId = req.params.idProduct
    Product.findById(productId, (err, product)=>{
        if (err) res.status(500).send({message:'Error al conectarase a la base de datos'})
        
        product.remove(err=>{
            if (err) res.status(500).send({message:'Error al borrar el producto'})
            res.status(200).send({message:'El producto fue eliminado'})
        })
    })
}

function insertProduct(req,res){
    let pro= new Product()
    pro.name=req.body.name
    pro.picture=req.body.picture
    pro.price=req.body.price
    pro.category=req.body.category
    pro.description=req.body.description

    pro.save((err,productStore)=>{
        if (err) res.status(500).send({message:'error al guardar en la base de datos'})

        res.status(200).send({pro:productStore})
    })
}

module.exports = {
    getProduct,
    getProducts,
    updateProduct,
    deleteProduct,
    insertProduct
}

