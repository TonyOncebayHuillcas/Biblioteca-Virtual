
module.exports ={
    port: process.env.PORT || 3000,
    //db: process.env.MONGODB || 'mongodb://localhost:27017/easynotebd',
    db: process.env.MONGODB || "mongodb+srv://cas:caspas@cluster0-6okcv.mongodb.net/test?retryWrites=true&w=majority",
    
    SECRET_TOKEN: 'miclavetoken'
    
}