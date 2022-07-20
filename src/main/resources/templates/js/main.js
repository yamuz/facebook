function savePost(mediaStream, postText, ownerEmail) {
    let json = {
        "mediaStream": mediaStream,
        "postText": postText,
        "ownerEmail": ownerEmail
    };

    fetch('/api/posts/save2', {
        method: 'POST',
        body: JSON.stringify(json),

        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiVVNFUiJ9.xifbY4mh0sXoRwmLgY-L-bfXhOvJNPjUyCosWe6-IYmz7NGeYGZZWpa7Qc1_XJjMu8dFyqp-DNvtg7NVVyQJ5g'
        }
    }).then((response) => {
        return response.json();
    });
}