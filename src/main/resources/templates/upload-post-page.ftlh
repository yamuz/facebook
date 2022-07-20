<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>

<div>
    <h4>Browse your file</h4>
    <input type="file" id="mediaStream" /> <br><br>
    <input type="text" id="postText" value="post..almaz is great">
    <input type="text" id="ownerEmail" value="yamuuzz@gmail.com">
    <button onclick="savePost(
        document.getElementById('mediaStream').files[0],
        document.getElementById('postText').value,
        document.getElementById('ownerEmail').value)">Send...</button>
</div>

<script>
    function savePost(mediaStream, postText, ownerEmail) {
        const formData = new FormData();
        formData.append('mediaStream', mediaStream);
        formData.append('postText', postText);
        formData.append('ownerEmail', ownerEmail);

        fetch('/api/posts/save2', {
            method: 'POST',
            body: formData,

            headers: {
               "Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiVVNFUiJ9.xifbY4mh0sXoRwmLgY-L-bfXhOvJNPjUyCosWe6-IYmz7NGeYGZZWpa7Qc1_XJjMu8dFyqp-DNvtg7NVVyQJ5g"
            }
        }).then(
            response => response.ok
        );
    }
</script>
</body>
</html>