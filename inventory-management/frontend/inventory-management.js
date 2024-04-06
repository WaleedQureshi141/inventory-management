// declaring the urls for both warehouses and items

const itemURL = 'http://localhost:8282/items';
let allItems = [];

const warehouseURL = 'http://localhost:8282/warehouses';
let allWarehouses = [];

// ITEM

// ADDING ITEMS TO TABLE
document.addEventListener('DOMContentLoaded', () => 
{
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () =>
    {
        // checks for response from server
        if (xhr.readyState === 4)
        {
            let items = JSON.parse(xhr.responseText);

            // adds each item to the table
            items.forEach(newItem =>
                {
                    addItemToTable(newItem);
                });
        }
    }

    xhr.open('GET', itemURL + '/all');
    xhr.send();
});

function addItemToTable (newItem)
{
    // creates new <td> for each column
    let tr = document.createElement('tr');
    let id = document.createElement('td');
    let name = document.createElement('td');
    let description = document.createElement('td');
    let warehouse = document.createElement('td');

    let editBtn = document.createElement('td');
    let delBtn = document.createElement('td');

    // needs to match backend name
    id.innerText = newItem.itemId;
    name.innerText = newItem.itemName;
    description.innerText = newItem.description;
    warehouse.innerText = newItem.warehouse.warehouseName;

    // creates new edit and delete buttons for later implementations
    editBtn.innerHTML = 
    `<button class="btn btn-outline-light btn-sm" id="editBtn" onClick="activateEditFormI(${newItem.itemId})" type="submit">EDIT</button>`

    delBtn.innerHTML = 
    `<button class="btn btn-outline-light btn-sm" id="editBtn" onClick="activateDelFormI(${newItem.itemId})" type="submit">DELETE</button>`

    // adds all the <td> into a <tr>
    tr.appendChild(id);
    tr.appendChild(name);
    tr.appendChild(description);
    tr.appendChild(warehouse);
    tr.appendChild(editBtn);
    tr.appendChild(delBtn);

    // sets the id attribute as the <tr> tag
    tr.setAttribute('id', 'TR' + newItem.id);

    document.getElementById('item-table-body').append(tr);

    allItems.push(newItem);
}

// CREATING A NEW ITEM
document.getElementById('new-item-form').addEventListener('submit', (event) =>
{
    // event.preventDefault();      nothing was updating with prevent default, hence it has been left out
    let inputData = new FormData(document.getElementById('new-item-form'));

    // uses name attribute from html
    // sets item data from new-item-form
    let newItem = 
    {
        itemName: inputData.get('add-item-name'),
        description: inputData.get('add-desc'),
        warehouse:
        {
            warehouseId: inputData.get('add-item-warehouse')
        }
    }

    doPostItem(newItem);
})

async function doPostItem(newItem)
{
    // waits for data to be returned from the POST request
    let returnedData = await fetch(itemURL + "/add", 
    {
        method: 'POST',
        headers: 
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newItem)
    })

    let itemJSON = await returnedData.json();
    console.log('ITEM JSON' + itemJSON);

    // adds the new item to the table
    addItemToTable(itemJSON);

    document.getElementById('new-item-form').reset();
}

// UPDATING AN ITEM
document.getElementById('update-item-form').addEventListener('submit', (event) =>
{
    // event.preventDefault();      nothing was updating with prevent default, hence it has been left out

    let inputData = new FormData(document.getElementById('update-item-form'))

    // uses name attribute from html
    // sets item data from update-item-form
    let item = 
    {
        itemId: document.getElementById('update-item-id').value,
        itemName: inputData.get('update-item-name'),
        description: inputData.get('update-desc'),
        warehouse:
        {
            warehouseName: inputData.get('update-item-warehouse')
        }
    }

    // sends the PUT request
    fetch(itemURL + '/update', 
    {
        method: 'PUT',
        headers:
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item)
    })
    // serializes response to json
    .then((data) =>
    {
        console.log('data');
        return data.json;   
    })
    .then((itemJson) => 
    {
        updateitemInTable(itemJson);
    })
    .catch((error) =>
    {
        console.error(error);
    })
})

function updateitemInTable(item)
{
    document.getElementById('TR' + item.itemId).innerHTML = 
    `<td>${item.itemId}</td>
    <td>${item.itemName}</td>
    <td>${item.warehouse.warehouseName}</td>
    <td><button class="btn btn-outline-light btn-sm" id="editBtn" onClick="activateEditFormI(${item.itemId})" type="submit">EDIT</button></td>
    <td><button class="btn btn-outline-light btn-sm" id="delBtn" onClick="activateDelFormI(${item.itemId})" type="submit">DELETE</button></td>`;
}

// auto adds fields from the table into the form
function activateEditFormI(itemId)
{
    for (let i of allItems)
    {
        if (i.itemId === itemId)
        {
            document.getElementById('update-item-id').value = i.itemId;
            document.getElementById('update-item-name').value = i.itemName;
            document.getElementById('update-desc').value = i.description;
            document.getElementById('update-item-warehouse').value = i.warehouse.warehouseName;
            
            // ATTEMPTED TO ADD DROPDOWN FOR A LIST OF WAREHOUSES THAT WOULD LOOK LIKE [WarehouseId - WarehouseName]
            // let warehouseOption = document.getElementById('update-item-warehouse');
            // warehouseOption.innerHTML = '';
            // for (let w of allWarehouses)
            // {
            //     let option = document.createElement('option');
            //     option.value = w.warehouseName;
            //     option.innerHTML = w.warehouseName;
            //     warehouseOption.options.add(option);
            // }
        }
    }
}

// DELETING AN ITEM
document.getElementById('del-item-form').addEventListener('submit', (event) =>
{
    // event.preventDefault();      nothing was updating with prevent default, hence it has been left out

    // uses name attribute from html
    let id = document.getElementById('del-item-id').value;
    let name = document.getElementById('del-item-name').value;
    let desc = document.getElementById('del-desc').value;
    let warehouse = document.getElementById('del-item-warehouse').value;

    // sets item data from del-item-form
    let item = 
    {
        itemId: id,
        itemName: name,
        description: desc,
        warehouse:
        {
            warehouseId: warehouse
        }
    }

    // sends the DELETE request
    fetch(itemURL + '/ship/' + id, 
    {
        method: 'DELETE',
        headers:
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify
    })
    // since no data is needed from backend, only the item needs to be removed from the table
    .then(() =>
    {
        removeItemFromTable(item);
        // resetAllForms();
    })
    .catch((error) =>
    {
        console.error(error);
    })
})

// removes the item from the table
function removeItemFromTable(item)
{
    const element = document.getElementById('TR' + item.itemId);
    element.remove();
}

// auto adds fields from the table into the form
function activateDelFormI(itemId)
{
    for (let i of allItems)
    {
        if (i.itemId === itemId)
        {
            document.getElementById('del-item-id').value = i.itemId;
            document.getElementById('del-item-name').value = i.itemName;
            document.getElementById('del-desc').value = i.description;
            document.getElementById('del-item-warehouse').value = i.warehouse.warehouseName;
        }
    }
}







// WAREHOUSE

// ADDING WAREHOUSES TO TABLE
document.addEventListener('DOMContentLoaded', () => 
{
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () =>
    {
        // checks for response from server
        if (xhr.readyState === 4)
        {
            let warehouses = JSON.parse(xhr.responseText);

            // adds each warehouse to the table
            warehouses.forEach(newWarehouse =>
                {
                    addWarehouseToTable(newWarehouse);
                });
        }
    }

    xhr.open('GET', warehouseURL + "/all");
    xhr.send();
});

function addWarehouseToTable (newWarehouse)
{
    // creates new <td> for each column
    let tr = document.createElement('tr');
    let id = document.createElement('td');
    let name = document.createElement('td');
    let location = document.createElement('td');

    let editBtn = document.createElement('td');
    let delBtn = document.createElement('td');

    // needs to match backend name
    id.innerText = newWarehouse.warehouseId;
    name.innerText = newWarehouse.warehouseName;
    location.innerText = newWarehouse.location;

    // creates new edit and delete buttons for later implementations
    editBtn.innerHTML = 
    `<button class="btn btn-outline-light btn-sm" id="editBtn" onClick="activateEditFormW(${newWarehouse.warehouseId})" type="submit">EDIT</button>`

    delBtn.innerHTML = 
    `<button class="btn btn-outline-light btn-sm" id="delBtn" onClick="activateDelFormW(${newWarehouse.warehouseId})" type="submit">DELETE</button>`

    // adds all the <td> into a <tr>
    tr.appendChild(id);
    tr.appendChild(name);
    tr.appendChild(location);
    tr.appendChild(editBtn);
    tr.appendChild(delBtn);

    // sets the id attribute as the <tr> tag
    tr.setAttribute('warehouse-id', 'TR' + newWarehouse.id);

    document.getElementById('warehouse-table-body').append(tr);

    allWarehouses.push(newWarehouse);
}

// CREATING A NEW WAREHOUSE
document.getElementById('new-warehouse-form').addEventListener('submit', (event) =>
{
    // event.preventDefault();      nothing was updating with prevent default, hence it has been left out
    let inputData = new FormData(document.getElementById('new-warehouse-form'));

    // uses name attribute from html
    // sets warehouse data from new-warehouse-form
    let newWarehouse = 
    {
        warehouseName: inputData.get('add-warehouse-name'),
        location: inputData.get('add-location')
    }

    doPostWarehouse(newWarehouse);
})

async function doPostWarehouse(newWarehouse)
{
    // waits for data to be returned from the POST request
    let returnedData = await fetch(warehouseURL + "/add", 
    {
        method: 'POST',
        headers: 
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newWarehouse)
    })

    let warehouseJSON = await returnedData.json();
    console.log('WAREHOUSE JSON' + warehouseJSON);

    // adds the new warehouse to the table
    addWarehouseToTable(warehouseJSON);

    document.getElementById('new-warehouse-form').reset();
}

// UPDATING A WAREHOUSE
document.getElementById('update-warehouse-form').addEventListener('submit', (event) =>
{
    // event.preventDefault();      nothing was updating with prevent default, hence it has been left out

    let inputData = new FormData(document.getElementById('update-warehouse-form'))

    // uses name attribute from html
    // sets warehouse data from update-warehouse-form
    let warehouse = 
    {
        warehouseId: document.getElementById('update-warehouse-id').value,
        warehouseName: inputData.get('update-warehouse-name'),
        location: inputData.get('update-location')
    }

    // sends the PUT request
    fetch(warehouseURL + '/update', 
    {
        method: 'PUT',
        headers:
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(warehouse)
    })
    // serializes response to json
    .then((data) =>
    {
        return data.json;   
    })
    .then((warehouseJson) => 
    {
        updateWarehouseInTable(warehouseJson);
    })
    .catch((error) =>
    {
        console.error(error);
    })
})

function updateWarehouseInTable(warehouse)
{
    document.getElementById('TR' + warehouse.warehouseId).innerHTML = 
    `<td>${warehouse.warehouseId}</td>
    <td>${warehouse.warehouseName}</td>
    <td>${warehouse.location}</td>
    <td><button class="btn btn-outline-light btn-sm" id="editBtn" onClick="activateEditFormW(${warehouse.warehouseId})" type="submit">EDIT</button></td>
    <td><button class="btn btn-outline-light btn-sm" id="delBtn" onClick="activateDelFormW(${warehouse.warehouseId})" type="submit">DELETE</button></td>`;
}

// auto adds fields from the table into the form
function activateEditFormW(warehouseId)
{
    for (let w of allWarehouses)
    {
        if (w.warehouseId === warehouseId)
        {
            document.getElementById('update-warehouse-id').value = w.warehouseId;
            document.getElementById('update-warehouse-name').value = w.warehouseName;
            document.getElementById('update-location').value = w.location;
        }
    }

    // document.getElementById('update-warehouse-form').style.display = 'block';        // will be changed so i can add an animation later
}

// DELETING A WAREHOUSE
document.getElementById('del-warehouse-form').addEventListener('submit', (event) =>
{
    // event.preventDefault();      nothing was updating with prevent default, hence it has been left out

    // uses name attribute from html
    let id = document.getElementById('del-warehouse-id').value;
    let name = document.getElementById('del-warehouse-name').value;
    let location = document.getElementById('del-location').value;

    // sets item data from del-warehouse-form
    let warehouse = 
    {
        warehouseId: id,
        warehouseName: name,
        location: location
    }

    // sends the DELETE request
    fetch(warehouseURL + '/delete/' + id, 
    {
        method: 'DELETE',
        headers:
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify
    })
    // since no data is needed from backend, only the warehouse needs to be removed from the table
    .then(() =>
    {
        removeWarehouseFromTable(warehouse);
        // resetAllForms();
    })
    .catch((error) =>
    {
        console.error(error);
    })
})

// removes the warehouse from the table
function removeWarehouseFromTable(warehouse)
{
    const element = document.getElementById('TR' + warehouse.warehouseId);
    element.remove();
}

// auto adds fields from the table into the form
function activateDelFormW(warehouseId)
{
    for (let w of allWarehouses)
    {
        if (w.warehouseId === warehouseId)
        {
            document.getElementById('del-warehouse-id').value = w.warehouseId;
            document.getElementById('del-warehouse-name').value = w.warehouseName;
            document.getElementById('del-location').value = w.location;
        }
    }
}

// function resetAllForms()
// {
//     // resetting the item forms
//     document.getElementById('new-item-form').reset();
//     document.getElementById('update-item-form').reset();
//     document.getElementById('del-item-form').reset();

//     // resetting the warehouse forms
//     document.getElementById('new-warehouse-form').reset();
//     document.getElementById('update-warehouse-form').reset();
//     document.getElementById('del-warehouse-form').reset();
// }