// frontend/script.js
const API_URL = 'http://localhost:8080/api/personas';

// Elementos del DOM
const personaForm = document.getElementById('persona-form');
const personasList = document.getElementById('personas-list');
const searchInput = document.getElementById('search-input');
const cancelBtn = document.getElementById('cancel-btn');
const formTitle = document.getElementById('form-title');
const submitBtn = document.getElementById('submit-btn');

let editingId = null;

// Event Listeners
document.addEventListener('DOMContentLoaded', loadPersonas);
personaForm.addEventListener('submit', handleFormSubmit);
cancelBtn.addEventListener('click', cancelEdit);

// Cargar todas las personas
async function loadPersonas() {
    try {
        const response = await fetch(API_URL);
        const personas = await response.json();
        displayPersonas(personas);
    } catch (error) {
        console.error('Error cargando personas:', error);
        alert('Error al cargar las personas');
    }
}

// Mostrar personas en la lista
function displayPersonas(personas) {
    personasList.innerHTML = '';
    
    if (personas.length === 0) {
        personasList.innerHTML = '<p>No se encontraron personas.</p>';
        return;
    }
    
    personas.forEach(persona => {
        const personaDiv = document.createElement('div');
        personaDiv.className = 'persona-item';
        personaDiv.innerHTML = `
            <div class="persona-info">
                <h3>${persona.nombre} ${persona.apellido}</h3>
                <p><strong>Email:</strong> ${persona.email || 'N/A'}</p>
                <p><strong>Teléfono:</strong> ${persona.telefono || 'N/A'}</p>
                <p><strong>Edad:</strong> ${persona.edad || 'N/A'}</p>
                <p><strong>Dirección:</strong> ${persona.direccion || 'N/A'}</p>
            </div>
            <div class="persona-actions">
                <button class="edit" onclick="editPersona(${persona.id})">Editar</button>
                <button class="delete" onclick="deletePersona(${persona.id})">Eliminar</button>
            </div>
        `;
        personasList.appendChild(personaDiv);
    });
}

// Manejar envío del formulario
async function handleFormSubmit(e) {
    e.preventDefault();
    
    const personaData = {
        nombre: document.getElementById('nombre').value,
        apellido: document.getElementById('apellido').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value,
        edad: document.getElementById('edad').value ? parseInt(document.getElementById('edad').value) : null,
        direccion: document.getElementById('direccion').value
    };
    
    try {
        if (editingId) {
            // Actualizar persona existente
            await fetch(`${API_URL}/${editingId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(personaData)
            });
        } else {
            // Crear nueva persona
            await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(personaData)
            });
        }
        
        resetForm();
        loadPersonas();
    } catch (error) {
        console.error('Error guardando persona:', error);
        alert('Error al guardar la persona');
    }
}

// Editar persona
function editPersona(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(persona => {
            document.getElementById('persona-id').value = persona.id;
            document.getElementById('nombre').value = persona.nombre;
            document.getElementById('apellido').value = persona.apellido;
            document.getElementById('email').value = persona.email || '';
            document.getElementById('telefono').value = persona.telefono || '';
            document.getElementById('edad').value = persona.edad || '';
            document.getElementById('direccion').value = persona.direccion || '';
            
            editingId = id;
            formTitle.textContent = 'Editar Persona';
            submitBtn.textContent = 'Actualizar';
            cancelBtn.style.display = 'inline-block';
        })
        .catch(error => {
            console.error('Error cargando persona:', error);
            alert('Error al cargar la persona para editar');
        });
}

// Eliminar persona
async function deletePersona(id) {
    if (!confirm('¿Estás seguro de que quieres eliminar esta persona?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadPersonas();
        } else {
            alert('Error al eliminar la persona');
        }
    } catch (error) {
        console.error('Error eliminando persona:', error);
        alert('Error al eliminar la persona');
    }
}

// Buscar personas
async function searchPersonas() {
    const query = searchInput.value.trim();
    if (!query) {
        loadPersonas();
        return;
    }
    
    try {
        // Buscar por nombre y apellido
        const [byNombre, byApellido] = await Promise.all([
            fetch(`${API_URL}/search/nombre?nombre=${query}`).then(r => r.json()),
            fetch(`${API_URL}/search/apellido?apellido=${query}`).then(r => r.json())
        ]);
        
        // Combinar y eliminar duplicados
        const combined = [...byNombre, ...byApellido];
        const uniquePersonas = combined.filter((persona, index, self) => 
            index === self.findIndex(p => p.id === persona.id)
        );
        
        displayPersonas(uniquePersonas);
    } catch (error) {
        console.error('Error buscando personas:', error);
        alert('Error al buscar personas');
    }
}

// Limpiar búsqueda
function clearSearch() {
    searchInput.value = '';
    loadPersonas();
}

// Cancelar edición
function cancelEdit() {
    resetForm();
}

// Resetear formulario
function resetForm() {
    personaForm.reset();
    editingId = null;
    formTitle.textContent = 'Agregar Nueva Persona';
    submitBtn.textContent = 'Guardar';
    cancelBtn.style.display = 'none';
    document.getElementById('persona-id').value = '';
}
