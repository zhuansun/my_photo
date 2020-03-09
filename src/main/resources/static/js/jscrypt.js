//获取密钥对
function getRsaKeys(func){
    window.crypto.subtle.generateKey(
        {
            name: "RSA-OAEP",
            modulusLength: 2048, //can be 1024, 2048, or 4096
            publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
            hash: {name: "SHA-512"}, //can be "SHA-1", "SHA-256", "SHA-384", or "SHA-512"
        },
        true, //whether the key is extractable (i.e. can be used in exportKey)
        ["encrypt", "decrypt"] //must be ["encrypt", "decrypt"] or ["wrapKey", "unwrapKey"]
    ).then(function(key){
        window.crypto.subtle.exportKey(
            "pkcs8",
            key.privateKey
        ).then(function(keydata1){
            window.crypto.subtle.exportKey(
                "spki",
                key.publicKey
            ).then(function(keydata2){
                var privateKey = RSA2text(keydata1,1);
                var publicKey = RSA2text(keydata2);
                func(privateKey,publicKey);
            }).catch(function(err){
                console.error(err);
            });
        })
            .catch(function(err){
                console.error(err);
            });
    })
        .catch(function(err){
            console.error(err);
        });
}
function RSA2text(buffer,isPrivate=0) {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    var base64 = window.btoa(binary);
    var text = "-----BEGIN "+(isPrivate?"PRIVATE":"PUBLIC")+" KEY-----\n";
    text += base64.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{64}\x01?/g,"$&\n");
    text += "\n-----END "+(isPrivate?"PRIVATE":"PUBLIC")+" KEY-----";
    return text;
}
