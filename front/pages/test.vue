<template>
  <input type="file" @change="handleFileChange" />
</template>

<script setup lang="ts">
const handleFileChange = async (event: Event) => {
    try {
        const file = (event.target as HTMLInputElement).files[0];
        const formData = new FormData();
        formData.append('file', file);
        
        const url = await $fetch(
          `http://localhost:8080/PasserellePro/Serv?op=ajouter_doc`,
          {
            method: "POST",
            credentials: "include",
          }
        );
        uploadFile(url, file);
    } catch (error) {
        console.error('Error uploading file:', error);
    }
};
async function uploadFile(presignedUrl: string, file: File) {
    try {
        const response = await fetch(presignedUrl, {
            method: 'PUT',
            body: file,
            headers: {
                'Content-Type': file.type,
            },
        });

        if (response.ok) {
            console.log('File upload succeeded');
        } else {
            console.error('File upload failed', response);
        }
    } catch (error) {
        console.error('Error uploading file:', error);
        throw error;
    }
}
</script>
