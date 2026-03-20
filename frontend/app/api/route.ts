import { NextRequest, NextResponse } from 'next/server';

async function handleRequest(request: NextRequest, props: { params: Promise<{ path: string[] }> }) {
  // No Next.js 15, params é uma Promise e deve ser aguardada (await)
  const { path } = await props.params;
  
  // Usando o IP que você já fixou no Dockerfile
  const BACKEND_IP = '172.31.136.143'; 
  const targetUrl = `http://${BACKEND_IP}:25000/${path.join('/')}`;

  const method = request.method;

  try {
    const fetchOptions: RequestInit = {
      method,
      headers: {
        'Content-Type': 'application/json',
      },
    };

    if (['POST', 'PUT', 'PATCH'].includes(method)) {
      const body = await request.json();
      fetchOptions.body = JSON.stringify(body);
    }

    const res = await fetch(targetUrl, fetchOptions);

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

export const GET = handleRequest;
export const POST = handleRequest;
export const DELETE = handleRequest;
export const PUT = handleRequest;