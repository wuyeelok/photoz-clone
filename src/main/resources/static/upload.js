const uploadBtnEl = document.getElementById("uploadBtn");
const fileUploadInputEl = document.getElementById("fileUpload");

/**
 * Check if file is valid
 * @param {File} file file
 * @return {String} Message after checking, return "valid" if valid
 */
function isValidFile(file) {
  let msg = "valid";

  const allowedFileType = new Set();
  allowedFileType.add("image/png");
  allowedFileType.add("image/jpeg");
  allowedFileType.add("image/svg+xml");
  allowedFileType.add("image/gif");
  allowedFileType.add("image/webp");
  allowedFileType.add("image/tiff");

  const maxFileSize = 1024 * 1024 * 200; // 200 MB

  // const dbColLength = 50; // DB column max length

  if (file === undefined || "" === file.name || 0 === file.size) {
    msg = "Uploaded File is empty!!";
  } else if (!allowedFileType.has(file.type)) {
    msg = "Invalid file format, image only!!";
  } else if (file.size > maxFileSize) {
    msg = `File's size exceeds limit of ${maxFileSize / 1024 / 1024}MB!!`;
  }
  // else if (file.name.length > dbColLength) {
  //   msg = `File's name exceeds limit of ${dbColLength} characters!!`;
  // }

  return msg;
}

async function uploadFile() {
  const formData = new FormData();
  const uploadFileList = fileUploadInputEl.files;
  console.log(uploadFileList);
  if (uploadFileList.length !== 1) {
    alert("Please choose a file to upload!");
    return;
  }
  const file = uploadFileList[0];
  const checkMsg = isValidFile(file);
  if ("valid" !== checkMsg) {
    alert(checkMsg);
    return;
  }

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
      if (response.status === 400 || response.status === 409) {
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
