import { NextRequest, NextResponse } from 'next/server';

// Função genérica para lidar com as requisições (Proxy)
async function handleRequest(request: NextRequest, { params }: { params: { path: string[] } }) {
  const { path } = params;
  
  // Pegamos o IP Privado da variável de ambiente que definiremos no Docker
  // Se não houver, ele tenta usar o localhost (para testes locais)
  const BACKEND_IP = process.env.BACKEND_PRIVATE_IP || 'localhost';
  const targetUrl = `http://${BACKEND_IP}:25000/${path.join('/')}`;

  const method = request.method;
  const headers = new Headers(request.headers);
  headers.delete('host'); // Evita conflitos de cabeçalho na AWS

  try {
    const fetchOptions: RequestInit = {
      method,
      headers: {
        'Content-Type': 'application/json',
      },
    };

    // Se a requisição tiver corpo (POST/PUT), nós o repassamos
    if (['POST', 'PUT', 'GET', 'DELETE  '].includes(method)) {
      const body = await request.json();
      fetchOptions.body = JSON.stringify(body);
    }

    const res = await fetch(targetUrl, fetchOptions);

    // Se o backend retornar erro, repassamos o erro
    if (!res.ok) {
      return NextResponse.json(
        { error: `Erro no Backend: ${res.statusText}` },
        { status: res.status }
      );
    }

    const data = await res.json();
    return NextResponse.json(data);

  } catch (error) {
    console.error("Erro no Proxy da API:", error);
    return NextResponse.json(
      { error: 'Não foi possível conectar ao backend na sub-rede privada.' },
      { status: 500 }
    );
  }
}

// Exportamos os métodos que seu sistema usa
export const GET = handleRequest;
export const POST = handleRequest;
export const DELETE = handleRequest;
export const PUT = handleRequest;