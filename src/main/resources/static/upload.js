const uploadBtnEl = document.getElementById("uploadBtn");
const fileUploadInputEl = document.getElementById("fileUpload");

async function uploadFile() {
  const formData = new FormData();
  const uploadFileList = fileUploadInputEl.files;
  console.log(uploadFileList);
  if (uploadFileList.length !== 1) {
    alert("Please choose a file to upload!");
    return;
  }
  const file = uploadFileList[0];
  console.log(`Uploaded file name: ${file.name}`);
  formData.append("data", file);

  try {
    uploadBtnEl.disabled = true;
    uploadBtnEl.innerText = "Uploading...";
    fileUploadInputEl.disabled = true;

    const response = await fetch("http://localhost:9182/photoz", {
      method: "POST",
      body: formData,
    });

    if (!response.ok) {
      if (response.status === 409) {
        const info = await response.json();
        throw new Error(info.message);
      }

      throw new Error("Something is wrong!");
    }

    const data = await response.json();

    alert(`Uploaded File: ${data.fileName}`);
  } catch (err) {
    alert(err.message);
  } finally {
    fileUploadInputEl.value = "";
    uploadBtnEl.innerText = "Upload";
    uploadBtnEl.disabled = false;
    fileUploadInputEl.disabled = false;
  }
}

uploadBtnEl.addEventListener("click", uploadFile);
